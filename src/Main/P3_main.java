package Main;

import java.io.IOException;

import dataManagement.DataManagement;

public class P3_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Running Program...");
		DataManagement dm = new DataManagement();
		try {
			System.out.println("Reading Files...");
			dm.fillPositions();
			System.out.println("Reading Files...33%");
			dm.fillCandidates();
			System.out.println("Reading Files...66%");
			dm.fillVotes();
			System.out.println("Reading Files...100%");
			System.out.println("Successfullly filled Positions, Candidates and Votes");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Generating Ballots from Votes...");
		dm.generateBallots();
		System.out.println("Distributing Ballots...");
		dm.distributeBallots();
		System.out.println("Counting Votes...");
		dm.CountVotes();
		System.out.println("Writing outputFiles");
		dm.writeResultsData();
		System.out.println("Done!! Check files to see your result!!!");
		
		
	}

}
