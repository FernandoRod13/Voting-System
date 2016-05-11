package dataManagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Stack;

import objects.Ballot;
import objects.Candidate;
import objects.CandidateComparator;
import objects.CandidateResult;
import objects.Output;
import objects.Positions;
import objects.Rank;
import objects.RankCandidateComparator;
import objects.RankComparator;
import objects.Vote;

/**
 * Class to store data in lists of objects and loads data from file.
 * 
 * @author Fernando Rodriguez
 *
 */
public class DataManagement {
	public ArrayList<Positions> positions;
	public ArrayList<ArrayList<Candidate>> candidates;
	public ArrayList<Vote> votes;
	public ArrayList<Ballot> ballots;
	public ArrayList<ArrayList<Ballot>> positionBallot;
	public ArrayList<Output> output;
	public ArrayList<ArrayList<Rank>> rank = new ArrayList<>();
	public static final String CANDIDATEFILE = "src/candidates.txt";
	private static final String POSITIONSFILE = "src/positions.txt";
	private static final String VOTESFILE = "src/votes.txt";
	private static final DataManagement DATA = new DataManagement();
	private int invalidBallot;
	private int blankBallot;

	/**
	 * Creates lists to store data from files of which the program is based on
	 */
	public DataManagement() {
		positions = new ArrayList<>();
		candidates = new ArrayList<>();
		votes = new ArrayList<>();
		ballots = new ArrayList<>();
		positionBallot = new ArrayList<>();
		invalidBallot = 0;
		output = new ArrayList<>();
		blankBallot = 0;
	}

	/**
	 * Returns a public instance of datamanagement object
	 * 
	 * @return datamanagment object
	 */
	public static DataManagement getInstance() {
		return DATA;
	}

	/**
	 * This Method will fill the list of political positions with the data from
	 * file
	 * 
	 * @throws IOException
	 *             if File not found or error in data from file.
	 */
	public void fillPositions() throws IOException {
		File file = new File(POSITIONSFILE);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = reader.readLine();
		while (line != null) {
			int index = Integer.parseInt(line);
			line = reader.readLine();
			Positions p = new Positions(index, line);
			positions.add(p);
			line = reader.readLine();
		}
		reader.close();
	}

	/**
	 * This Method will fill the list of candidates with the data from file
	 * 
	 * @throws IOException
	 *             if File not found or error in data from file.
	 */
	public void fillCandidates() throws IOException {
		File file = new File(CANDIDATEFILE);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = reader.readLine();
		for (int i = 0; i < positions.size(); i++)
			candidates.add(new ArrayList<>());
		while (line != null) {
			String name = line;
			line = reader.readLine();
			int positionIndex = Integer.parseInt(line);
			line = reader.readLine();
			int candidateIndex = Integer.parseInt(line);
			line = reader.readLine();
			Candidate c = new Candidate(name, positionIndex, candidateIndex);
			candidates.get(c.getPositionsIndex() - 1).add(c);
		}
		reader.close();
		for (ArrayList<Candidate> a : candidates)
			a.sort(new CandidateComparator());
	}

	/**
	 * This Method will fill the list of votes with the data from file
	 * 
	 * @throws IOException
	 *             if File not found or error in data from file.
	 */
	public void fillVotes() throws IOException {
		File file = new File(VOTESFILE);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = reader.readLine();
		while (line != null) {
			String[] contents = line.split(" ");
			int position = Integer.parseInt(contents[0]);
			int ID = Integer.parseInt(contents[1]);
			int candidate = Integer.parseInt(contents[2]);
			int rank = Integer.parseInt(contents[3]);
			votes.add(new Vote(ID, position, candidate, rank, findCandidate(candidate, position).getName()));
			line = reader.readLine();
		}
		reader.close();
	}

	private Candidate findCandidate(int index, int position) {
		for (Candidate c : candidates.get(position - 1)) {
			if (c.getCandidateIndex() == index)
				return c;
		}
		return null;
	}

	/**
	 * Verify if a ballot exists, if it does returns it.
	 * 
	 * @param id
	 *            The identification number for the ballot to check
	 * @return ballot if exists, null otherwise
	 */
	private Ballot existingBallot(int id) {
		for (Ballot b : ballots) {
			if (b.getId() == id)
				return b;
		}
		return null;
	}

	/**
	 * Method will make ballots from all the votes read from the file.
	 */
	public void generateBallots() {
		for (Vote v : votes) {
			Ballot b = existingBallot(v.getBallotID());
			if (b != null)
				b.getVotes().add(v);
			else {
				b = new Ballot(v.getBallotID(), v);
				ballots.add(b);
			}

		}
	}
	/**
	 * Method will distribute ballots per position if ballot is not valid or is
	 * not blank
	 */
	public void distributeBallots() {
		for (int i = 0; i < positions.size(); i++)
			positionBallot.add(new ArrayList<>());
		for (Ballot b : ballots) {
			if (!validBallot(b)) {
				invalidBallot++;
			} else if (blankBallot(b)) {
				blankBallot++;
			} else {
				b.sortVotes();
				positionBallot.get((b.getVotes().get(0).getPosition()) - 1).add(b);
			}
		}
	}

	/**
	 * Method will check if a ballot is valid.
	 * 
	 * @param b
	 *            Ballot to validate
	 * @return if the ballot is valid then returns true, false otherwise.
	 */
	private boolean validBallot(Ballot b) {
		b.rankSortVotes();
		int zeros = 0;
		ArrayList<Vote> v = b.getVotes();
		for (int i = 0; i < v.size(); i++)
			if (v.get(i).getRank() == 0)
				zeros++;
			else if (v.get(i).getRank() > (candidates.get(v.get(i).getPosition() - 1).size() - zeros))
				return false;
			else if (v.get(i).getRank() != 0 && i != 0 && v.get(i).getRank() == v.get(i - 1).getRank())
				return false;
		return true;
	}

	/**
	 * Method will check if a ballot is blank
	 * 
	 * @param b
	 *            ballot in question
	 * @return true if all vote ranks are 0, false otherwise
	 */
	private boolean blankBallot(Ballot b) {
		for (Vote v : b.getVotes())
			if (v.getRank() != 0)
				return false;
		return true;
	}

	/**
	 * This method will remove a candidate form the counting process when
	 * necessary
	 * 
	 * @param candidate
	 *            index of the Candidate
	 * @param position
	 *            index of the position Candidate was running for
	 */
	private void removeCandidate(int candidate, int position) {
		ArrayList<Ballot> list = positionBallot.get(position - 1);
		for (Ballot b : list) {
			b.removeCandidate(candidate, candidates.get(position - 1).size());
		}
		candidates.get(position - 1).remove(candidate - 1);
		for (int i = candidate - 1; i < candidates.get(position - 1).size(); i++)
			candidates.get(position - 1).get(i).adjustCandidateIndex();
		for (int i = 0; i < rank.size(); i++) {
			Rank r = findRank(candidate, i);
			rank.get(i).remove(r);
		}

	}

	/**
	 * Method is called to count the votes per position and decide winning
	 * candidate. Method will also fill list with output data
	 */
	public void CountVotes() {
		int round = 1;
		// iterate through for each position
		for (int i = 0; i < positionBallot.size(); i++) {
			ArrayList<Ballot> list = positionBallot.get(i);
			// iterate through each ballot of this position until one candidate
			// remains
			while (!done(i)) {
				System.out.println(
						"Position: " + (i + 1) + " Candidates: " + candidates.get(i).size() + " round: " + round);
				initializeRankMatrix(candidates.get(i).size(), i + 1);
				// count votes per ballot
				for (Ballot b : list) {
					fillVotesData(b.getVotes());
				}
				// process output
				processData(round, i + 1);
				resetData();
				round++;
			}
			round = 1;
		}

	}

	private boolean done(int position) {
		Output o = findObject(position+1);
		if (o == null)
			return false;
		else {
			if (o.getWinner() == null)
				return false;
		}
		return true;
	}

	/**
	 * Method used to find a specific output object within list.
	 * 
	 * @param position
	 *            index of
	 * @return Output object if it exists, null otherwise
	 */
	private Output findObject(int position) {
		for (Output o : output) {
			if (o.getPosition() == position)
				return o;
		}
		return null;
	}

	/**
	 * Method used to find a Rank object form list
	 * 
	 * @param candidate
	 *            candidate of interest
	 * @return rank containing the index of candidate and amount of rank 1
	 *         preference by voter null otherwise.
	 */
	private Rank findRank(int candidate, int rankNum) {
		ArrayList<Rank> list = rank.get(rankNum);
		for (Rank r : list) {
			if (r.getCandidateIndex() == candidate)
				return r;
		}
		return null;
	}

	/**
	 * 
	 * @param candidateAmount
	 */
	private void initializeRankMatrix(int candidateAmount, int position) {

		for (int i = 0; i <= candidateAmount; i++) {
			rank.add(new ArrayList<>());
			for (int j = 0; j < candidateAmount; j++) {
				rank.get(i).add(new Rank(j + 1, 0, getCandidateName(j + 1, position)));
			}
		}
	}

	/**
	 * Method used to count all votes for all candidates for all ranks
	 * 
	 * @param list
	 *            list of votes to add data
	 */
	private void fillVotesData(ArrayList<Vote> list) {
		for (Vote v : list) {
			Rank r = findRank(v.getCandidate(), v.getRank());
			if (r != null)
				r.incrementAmount();
			else
				rank.get(v.getRank()).add(new Rank(v.getCandidate(), 1, v.getName()));
		}
	}

	/**
	 * 
	 * @param index
	 * @param position
	 * @return
	 */
	private String getCandidateName(int index, int position) {
		for (Candidate c : candidates.get(position - 1)) {
			if (c.getCandidateIndex() == index)
				return c.getName();
		}
		return null;
	}

	/**
	 * This method will determine which candidate is removed in each round and
	 * then record the data
	 * 
	 * @param round
	 *            identifies how many candidates have been eliminated
	 * @param position
	 *            data being proceed corresponds to this position
	 */
	private void processData(int round, int position) {
		int voteAmount = positionBallot.get(position - 1).size();
		boolean won = false;
		int i = 1;
		int looserAmount = 0;
		Rank looser = null;
		Rank winner = null;
		ArrayList<CandidateResult> loosers = new ArrayList<>();// if no tie
		CandidateResult win = null;
		boolean decision = false;
		// decide if winner or not

		looserAmount = rank.get(i).get(0).getRankAmount();
		int data = winLoose(i, looserAmount, voteAmount, position);
		if (data > 0) {
			won = true;
			winner = findRank(data, i);
			decision = true;
		} else if (data < 0) {
			looser = findRank(Math.abs(data), i);
		} else {
			System.out.println("failed");
		}

		// generate output data
		if (won) {
			win = new CandidateResult(winner.getCandidateIndex(),
					findRank(winner.getCandidateIndex(), 1).getRankAmount(), round, winner.getName());
			removeCandidate(winner.getCandidateIndex(), position);
			if (loosers.size() > 1) {
				for (Rank r : rank.get(1)) {
					loosers.add(new CandidateResult(r.getCandidateIndex(), r.getRankAmount(), round, r.getName()));
				}
				while (rank.get(1).size() > 0) {
					removeCandidate(rank.get(1).get(0).getCandidateIndex(), position);
				}
			} else {
				Rank r = rank.get(1).get(0);
				loosers.add(new CandidateResult(r.getCandidateIndex(), r.getRankAmount(), round, r.getName()));
				// removeCandidate(r.getCandidateIndex(),position);
			}
		}
		Output out = findObject(position);

		if (out == null) {
			if (won) {
				output.add(new Output(position, voteAmount, invalidBallot, blankBallot, win, loosers));
			} else {
				output.add(
						new Output(position, voteAmount, invalidBallot, blankBallot,
								new CandidateResult(looser.getCandidateIndex() + round,
										findRank(looser.getCandidateIndex(), 1).getRankAmount(), round,
										looser.getName())));
				removeCandidate(looser.getCandidateIndex(), position);
			}
		} else {
			if (won) {
				out.addResults(loosers);
				out.setWinner(win);
			} else {
				out.getResults().add(new CandidateResult(looser.getCandidateIndex() + round,
						findRank(looser.getCandidateIndex(), 1).getRankAmount(), round, looser.getName()));
				removeCandidate(looser.getCandidateIndex(), position);
			}
		}

	}

	/**
	 * This method is use to determine if there is a winner or loosers.
	 * 
	 * @param rankIndex
	 * @param looserVotes
	 * @param voteAmount
	 * @return
	 * 
	 */
	private int winLoose(int rankIndex, int looserVotes, int voteAmount, int position) {
		Rank winner = null;
		ArrayList<Rank> looser = new ArrayList<>();
		int index = 0;
		int result = 0;
		ArrayList<Rank> rlist = new ArrayList<>();
		for (Rank r : rank.get(rankIndex)) {

			if ((float) r.getRankAmount() / (float) voteAmount > 0.5) {
				winner = r;
				result = r.getCandidateIndex();
			}
			if (r.getRankAmount() == looserVotes)
				looser.add(looser.size(), r);
			if (r.getRankAmount() < looserVotes) {
				looserVotes = r.getRankAmount();
				// empty list
				looser.clear();
				looser.add(r);
			}
			index++;
		}
		if (winner == null) {
			if (looser.size() > 1) {
				boolean lost = false;
				int thisRank = rankIndex + 1;

				for (Rank r : looser)
					rlist.add(r);
				while (!lost) {
					if (thisRank <= candidates.get(position-1).size()) {
						for (int i = 0; i < rlist.size(); i++)
							rlist.add(i, findRank(rlist.remove(i).getCandidateIndex(), thisRank));
						if (rlist.size() > 1)
							rlist.sort(new RankComparator());
						rlist = determineLooser(rlist);
					} else {

						rlist.sort(new RankCandidateComparator());
						result = rlist.get(rlist.size() - 1).getCandidateIndex();
						lost = false;
					}
					thisRank++;
					if (rlist.size() == 1) {
						result = rlist.get(0).getCandidateIndex();
						result = result - 2 * result;
						lost = true;
					}
				}

			} else {
				result = looser.get(0).getCandidateIndex();
				result = result - result * 2;
			}
		}

		return result;

	}

	/**
	 * Clear data after each round
	 */
	private void resetData() {
		rank.clear();
	}

	private ArrayList<Rank> determineLooser(ArrayList<Rank> loose) {
		Stack<Rank> stack = new Stack<>();
		stack.push(loose.get(0));
		for (int i = 1; i < loose.size(); i++) {
			if (stack.peek().getRankAmount() == loose.get(i).getRankAmount()) {
				stack.push(loose.get(i));
			}
		}
		loose.clear();
		while (!stack.isEmpty()) {
			loose.add(stack.pop());
		}

		return loose;
	}

	/**
	 * Writes output to respective files
	 */
	public void writeResultsData() {
		for (Positions p : positions) {
			File file = new File("src/" + p.getTitle() + ".txt");
			try {
				PrintStream out = new PrintStream(file);
				out.println(findObject(p.getIndex()).toString());
				out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
