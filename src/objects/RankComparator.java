package objects;

import java.util.Comparator;
/**
 * Ascending Rank vote amount comparatort
 * @author Fernando Rodriguez
 *
 */
public class RankComparator implements Comparator<Rank>{

	@Override
	public int compare(Rank o1, Rank o2) {
		// TODO Auto-generated method stub
		return o1.getRankAmount()-o2.getRankAmount();
	}

}
