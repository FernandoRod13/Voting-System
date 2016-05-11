package objects;

import java.util.Comparator;
/**
 * Asendind Rank Candidate index sorter
 * @author Fernando Rodriguez
 *
 */
public class RankCandidateComparator implements Comparator<Rank> {

	@Override
	public int compare(Rank o1, Rank o2) {
		// TODO Auto-generated method stub
		return o1.getCandidateIndex()-o2.getCandidateIndex();
	}

}
