package objects;

import java.util.Comparator;
/**
 * Ascending vote Rank sorting comparator
 * @author fernandorodriguez
 *
 */
public class VoteRankComparator implements Comparator<Vote> {

	@Override
	public int compare(Vote o1, Vote o2) {
		// TODO Auto-generated method stub
		return o1.getRank()-o2.getRank();
	}

}
