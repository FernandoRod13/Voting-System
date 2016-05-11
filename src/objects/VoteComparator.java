package objects;

import java.util.Comparator;
/**
 * Asending Vote Candidate index sorting comparator
 * @author Fernando Rodriguez
 *
 */
public class VoteComparator implements Comparator<Vote> {

	@Override
	public int compare(Vote o1, Vote o2) {
		return o1.getCandidate()-o2.getCandidate();
	}

}
