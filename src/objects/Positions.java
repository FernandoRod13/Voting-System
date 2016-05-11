package objects;

/**
 * This class will represent the position to be held by a candidate
 * @author Fernando Rodriguez
 *
 */
public class Positions{
	private int index;
	private String title;
	/**
	 * Object used for to store titles to be held by candidates
	 * @param index index which corresponds to the desired title for the Candidate
	 * @param title Corresponding title for this position
	 */
	public Positions(int index, String title){
		this.index = index;
		this.title = title;
	}
	/**
	 * Returns the index for this position
	 * @return position index
	 */
	public int getIndex(){
		return this.index;
	}
	/**
	 * Returns the name of the title for this position
	 * @return Title of the position
	 */
	public String getTitle(){
		return this.title;
	}
}
