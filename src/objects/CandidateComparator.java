package objects;

import java.util.Comparator;
/**
 * Assending candidnadte index order comparator class
 * @author Fernando Rodriguez
 *
 */
public class CandidateComparator implements Comparator<Candidate> {

	@Override
	public int compare(Candidate o1, Candidate o2) {
		// TODO Auto-generated method stub
		return o1.getCandidateIndex()-o2.getCandidateIndex();
	}

}
