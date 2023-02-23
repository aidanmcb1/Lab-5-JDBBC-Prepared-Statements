package edu.cscc;

import java.sql.*;
import java.util.Formatter;
import java.util.Scanner;

public class main {

	final static String user = System.getenv("userenv");
	final static String pass = System.getenv("passenv");
	final static String port = System.getenv("portenv");
	final static String host = System.getenv("hostenv");
	final static String database = System.getenv("databaseenv");

	final static String connectionURL = "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + database + ";user="
			+ user + ";password=" + pass + ";encrypt=true;TrustServerCertificate=true";


	
	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		System.out.println("Customer Search By Country");
		System.out.print("Enter Country: ");
		String countryin = input.nextLine();

		try (Connection conn = DriverManager.getConnection(connectionURL);
				PreparedStatement stmt = conn.prepareStatement(
						"select CompanyName, Address, City, Region, PostalCode, Country from customers where country = ?");) {
			stmt.setString(1, countryin);
			ResultSet rs = stmt.executeQuery();

			Formatter fmtheader = new Formatter();
			Formatter fmtbody = new Formatter();
			fmtheader.format("%s%25s%27s%32s%35s%26s", "Company Name", "Address", "City", "Region", "Postal Code",
					"Country");
			
			System.out.println(fmtheader);
			System.out.println("=================================================================================="
					+ "==================================================================================");

			while (rs.next()) {
				String companyName = rs.getString("CompanyName");
				String address = rs.getString("Address");
				String city = rs.getString("City");
				String region = rs.getString("Region");
				String postal = rs.getString("PostalCode");
				String country = rs.getString("Country");

				if (region == null) {
					region = "n/a";
				}
				fmtbody.format("%-30s%-30s%-30s%-30s%-30s%-30s\n", companyName, address, city, region, postal, country);


			}
			System.out.println(fmtbody);

			conn.close();
			input.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}

	}

}
