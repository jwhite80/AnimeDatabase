


public class Anime implements Comparable <Anime>
{
	private String name;
	private int episodesTotal, episodesObtained, episodesWatched;
	private String mediaType, completionStatus, upToDateStatus;
	
	//method separation
	
	public Anime()
	{
		this.name = "No Anime";
		
		this.episodesTotal = 2;
		this.episodesObtained = 1;
		this.episodesWatched = 1;
		
		this.mediaType = "----";
		this.completionStatus = "---";
		this.upToDateStatus = "---";
	}
	
	//method separation
	
	public Anime(String newName, int totalEps, int obtainedEps, int watchedEps,
					 String otherMediaType, String otherCompletionStatus, String otherUpToDateStatus)
	{
		this.name = newName;
		
		this.episodesTotal = totalEps;
		this.episodesObtained = obtainedEps;
		this.episodesWatched = watchedEps;
		
		this.mediaType = otherMediaType;
		this.completionStatus = otherCompletionStatus;
		this.upToDateStatus = otherUpToDateStatus;
	}
	
	//method separation
	
	public String getName()
	{
		return this.name;
	}
	
	//method separation
	
	public int getTotal()
	{
		return this.episodesTotal;
	}
	
	//method separation
	
	public int getObtained()
	{
		return this.episodesObtained;
	}
	
	//method sepataion
	
	public int getWatched()
	{
		return this.episodesWatched;
	}
	
	//method separation
	
	public String getMediaType()
	{
		return this.mediaType;
	}
	
	//method separation
	
	public String getCompletionStatus()
	{
		return this.completionStatus;
	}
	
	//method separation
	
	public String getUpToDateStatus()
	{
		return this.upToDateStatus;
	}
	
	//method separation
	
	public void setName(String newName)
	{
		this.name = newName;
	}
	
	//method separation
	
	public void setTotal(int newTotal)
	{
		this.episodesTotal = newTotal;
	}
	
	//method separation
	
	public void setObtained(int newObtained)
	{
		this.episodesObtained = newObtained;
	}
	
	//method separation
	
	public void setWatched(int newWatched)
	{
		this.episodesWatched = newWatched;
	}
	
	//method separation
	
	public void setMediaType(String otherMedia)
	{
		this.mediaType = otherMedia;
	}
	
	//method separation
	
	public void changeCompletionStatus()
	{
		if(this.completionStatus.compareTo("---") == 0)
		{
			this.completionStatus = "[ ]";
		}
		else if(this.completionStatus.compareTo("[ ]") == 0)
		{
			this.completionStatus = "[x]";
		}
		else if(this.completionStatus.compareTo("[x]") == 0)
		{
			this.completionStatus = "[ ]";
		}
	}
	
	//method separation
	
	public void changeUpToDateStatus()
	{
		if(this.upToDateStatus.compareTo("---") == 0)
		{
			this.upToDateStatus = "[ ]";
		}
		else if(this.upToDateStatus.compareTo("[ ]") == 0)
		{
			this.upToDateStatus = "[x]";
		}
		else if(this.upToDateStatus.compareTo("[x]") == 0)
		{
			this.upToDateStatus = "[ ]";
		}
	}
	
	//method separation
	
	public void advanceKnown()
	{
		this.episodesTotal += 1;
	}
	
	//method separation
	
	public void advanceObtained()
	{
		this.episodesObtained += 1;
	}
	
	//method separation
	
	public void advanceWatched()
	{
		this.episodesWatched += 1;
	}
	
	//method separation
	
	public boolean equals(Object other)
	{
		if(other instanceof Anime)
		{
			Anime otherAnime = (Anime)other;
			
			return name.toLowerCase().compareTo(otherAnime.name.toLowerCase()) == 0;
		}
		return false;
	}
	
	//method separation
	
	public int compareTo(Anime other)
	{
		if(name.toLowerCase().compareTo(other.name.toLowerCase()) != 0)
		{
			return name.compareTo(other.name);
		}
		else
		{
			return 0;
		}
	}//end compareTo
	
	//method separation
	
	public String toString()
	{
		return this.episodesTotal + " : " + this.episodesObtained
		+ " : " + this.episodesWatched
		+ " : " + this.mediaType
		+ " : " + this.completionStatus
		+ " : " + this.upToDateStatus
		+ " | " + this.name + "\n";
	}
}