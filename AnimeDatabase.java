

import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AnimeDatabase
{
	private Anime [] list;
	private int longestAnimeName, longestKnown, longestObtained, longestWatched;
	
	//method separation
	
	public AnimeDatabase()
	{
		this.list = new Anime[1];
		
		for(int i = 0; i < 1; i++)
		{
			list[i] = new Anime();
		}
	}
	
	//method separation
	
	public void findLongestName()
	{
		this.longestAnimeName = 0;
		
		for(int i = 0; i < list.length; i++)
		{
			if(list[i].getName().length() > longestAnimeName)
			{
				this.longestAnimeName = list[i].getName().length();
			}
		}
	}
	
	//method separation
	
	public int getLongestAnimeName()
	{
		return this.longestAnimeName;
	}
	
	//method separation
	
	public void fillAnimeList(String inputFile)throws FileNotFoundException
	{
		Scanner listing =  new Scanner(new File(inputFile));
		
		int animeCount = 0;
		
		while(listing.hasNextLine())
		{
			animeCount++;
			
			listing.nextLine();
		}
		
		listing.close();
		
		this.list = new Anime[animeCount];
		
		listing = new Scanner(new File(inputFile));
		
		Scanner decoy = new Scanner(System.in);
		
		Pattern whitespace = decoy.delimiter();
		
		for(int i = 0; i < animeCount; i++)
		{
			listing.useDelimiter("_");
			String name = listing.next();

			listing.useDelimiter(whitespace);
			String total = listing.next();
			String refinedTotal = "";
			
			for(int j = 1; j < total.length(); j++)
			{
				refinedTotal += total.charAt(j);
			}
			
			int episodeCount = Integer.parseInt(refinedTotal);
			int episodesInPossession = Integer.parseInt(listing.next());
			int episodesViewed = Integer.parseInt(listing.next());
			
			String dataType = listing.next();
			
			listing.useDelimiter("_");
			
			String dummy2 = listing.next();
			String completionStat = "";
			for(int k = 1; k < dummy2.length(); k++)
			{
				completionStat += dummy2.charAt(k);
			}
			
			String upToDateStat = listing.next();
			
			list[i] = new Anime(name, episodeCount, episodesInPossession, episodesViewed,
									  dataType, completionStat, upToDateStat);
			listing.nextLine();
		}
		
		listing.close();
		
	}
	
	//method separation
	
	public void createNewDatabase()
	{		
		Anime [] temp = new Anime[list.length + 1];
		
		for(int i = 0; i < list.length; i++)
		{
			temp[i] = list[i];
		}
		
		this.list = temp;
		
		list[list.length - 1] = new Anime();
		
		temp = new Anime[1];
	}
	
	//method separation
	
	public void addNewSeries(String name)
	{
		int count = 0;
		for(int i = 0; i < list.length; i++)
		{
			if(name.toLowerCase().compareTo(list[i].getName().toLowerCase()) == 0)
			{
				count++;
			}
		}
		if(count > 0)
		{
			System.out.println("That anime already exists in the database.");
			System.out.println();
		}
		else
		{
			list[list.length - 1].setName(name);
		}
	}
	
	//method separation
	
	public void sortAnime()
	{
		int start, current;
		
		for(start = 0; start < list.length - 1; start++)
		{
			current = start;
			
			for(current = start +1; current < list.length; current++)
			{
				if(list[current].getName().toLowerCase().compareTo(list[start].getName().toLowerCase()) < 0)
				{
					Anime temp = list[current];
					list[current] = list[start];
					list[start] = temp;
				}//end if
			}//end inner for
		}//end outer for
		
	}
	
	//method separation
	
	public int findAnime(Anime target)//binary search here
	{
		int lowIndex = 0;
		int highIndex = list.length - 1;
		int midIndex;
		
		while(lowIndex <= highIndex)
		{
			midIndex = (lowIndex + highIndex) / 2;
			String startOfCurrentAnimeName = "";
			for(int i = 0; i < target.getName().length(); i++)
			{
				if(startOfCurrentAnimeName.toLowerCase().compareTo(list[midIndex].getName().toLowerCase()) == 0 &&
					startOfCurrentAnimeName.length() < target.getName().length())
				{
					i = target.getName().length();
					
					while(startOfCurrentAnimeName.length() < target.getName().length())
					{
						startOfCurrentAnimeName += " ";
					}
				}
				else
				{
					startOfCurrentAnimeName += list[midIndex].getName().charAt(i);
				}
			}
			
			if(startOfCurrentAnimeName.toLowerCase().compareTo(target.getName().toLowerCase()) > 0)
			{
				highIndex = midIndex - 1;
			}
			else if(startOfCurrentAnimeName.toLowerCase().compareTo(target.getName().toLowerCase()) < 0)
			{
				lowIndex = midIndex + 1;
			}
			else
			{
				int beginningIndex = midIndex;
				int previousIndex;
				
				String previousName = "";
				
				if(beginningIndex == 0)
				{
					return beginningIndex;
				}
				
				while(previousName.compareTo("") == 0)
				{
					previousIndex = beginningIndex - 1;
					
					for(int j = 0; j < startOfCurrentAnimeName.length(); j++)
					{
						previousName += list[previousIndex].getName().charAt(j);
						
						if(previousName.compareTo(list[previousIndex].getName()) == 0)
						{
							j = startOfCurrentAnimeName.length();
						}
					}
					
					if(previousName.toLowerCase().compareTo(startOfCurrentAnimeName.toLowerCase()) == 0)
					{
						beginningIndex = beginningIndex - 1;
						previousName = "";
					}
					else
					{
						return beginningIndex;
					}
				}//end while
			}//end else
		}//end while
		
		return -1;

	}
	
	//method separation
	
	public void addAnimeKnown(int index)
	{
		list[index].advanceKnown();
	}
	
	//method separation
	
	public void addAnimeObtained(int index)
	{
		list[index].advanceObtained();
	}
	
	//method separation
	
	public void addAnimeWatched(int index)
	{
		list[index].advanceWatched();
	}
	
	//method separation
	
	public void changeAnimeKnown(int index, int episodes)
	{
		list[index].setTotal(episodes);
	}
	
	//method separation
	
	public void changeAnimeObtained(int index, int episodes)
	{
		list[index].setObtained(episodes);
	}
	
	//method separation
	
	public void changeAnimeWatched(int index, int episodes)
	{
		list[index].setWatched(episodes);
	}
	
	//method separation
	
	public int getListLength()
	{
		return list.length;
	}
	
	public String getAnimeName(int i)
	{
		return list[i].getName();
	}
	
	//method separation
	
	public int getAnimeKnown(int i)
	{
		return list[i].getTotal();
	}
	
	//method separation
	
	public int getAnimeObtained(int i)
	{
		return list[i].getObtained();
	}
	
	//method separation
	
	public int getAnimeWatched(int i)
	{
		return list[i].getWatched();
	}
	
	//method separation
	
	public String getFileFormat(int i)
	{
		return list[i].getMediaType();
	}
	
	//method separation
	
	public String getCompletionString(int i)
	{
		return list[i].getCompletionStatus();
	}
	
	//method separation
	
	public void changeMediaFormat(int i, String format)
	{
		list[i].setMediaType(format);
	}
	
	//method separation
	
	public String getCompletionStat(int i)
	{
		return list[i].getCompletionStatus();
	}
	
	//method separation
	
	public void changeCompletionStat(int i)
	{
		list[i].changeCompletionStatus();
	}
	
	//method separation
	
	public String getUpToDateStat(int i)
	{
		return list[i].getUpToDateStatus();
	}
	
	//method separation
	
	public void changeUpToDateStat(int i)
	{
		list[i].changeUpToDateStatus();
	}
	
	//method separation
	
	@Override
	public String toString()
	{
		String animeList = "";
		
		String currentKnown = "";
		String currentObtained = "";
		String currentWatched = "";
		
		this.longestKnown = 0;
		this.longestObtained = 0;
		this.longestWatched = 0;
		
		for(int i = 0; i < list.length; i++)
		{
			currentKnown += list[i].getTotal();
			currentObtained += list[i].getObtained();
			currentWatched += list[i].getWatched();
			
			if(i == 0)
			{
				longestKnown = currentKnown.length();
				longestObtained = currentObtained.length();
				longestWatched = currentWatched.length();
			}
			else if(i >= 1)
			{
				if(currentKnown.length() > longestKnown)
				{
					this.longestKnown = currentKnown.length();
				}
				if(currentObtained.length() > longestObtained)
				{
					this.longestObtained = currentObtained.length();
				}
				if(currentWatched.length() > longestWatched)
				{
					this.longestWatched = currentWatched.length();
				}
			}
			
			currentKnown = "";
			currentObtained = "";
			currentWatched = "";
		}//end i for
		
		for(int j = 0; j < list.length; j++)
		{
			currentKnown += list[j].getTotal();
			currentObtained += list[j].getObtained();
			currentWatched += list[j].getWatched();
			
			if(currentKnown.length() < longestKnown)
			{
				for(int k = 0; k < (longestKnown - currentKnown.length()); k++)
				{
					animeList += " ";
				}
			}
			
			animeList += list[j].getTotal() + " : ";
			
			if(currentObtained.length() < longestObtained)
			{
				for(int k = 0; k < (longestObtained - currentObtained.length()); k++)
				{
					animeList+= " ";
				}
			}
			
			animeList += list[j].getObtained() + " : ";
			
			if(currentWatched.length() < longestWatched)
			{
				for(int k = 0; k < (longestWatched - currentWatched.length()); k++)
				{
					animeList += " ";
				}
			}
			
			animeList += list[j].getWatched() + " : ";
			animeList += list[j].getMediaType() + " : ";
			animeList += list[j].getCompletionStatus() + " : ";
			animeList += list[j].getUpToDateStatus() + " | ";
			animeList += list[j].getName() + "\n";
						
			currentKnown = "";
			currentObtained = "";
			currentWatched = "";
			
		}//end j for
		
		return animeList;
	}
	
	//method separation
	
	public String toSingleString(int index)
	{
		return list[index].toString();
	}
	
	//method separation
	
	public String toCriticalString(int [] miniDatabase)
	{
		String criticalList = "";
		
		String currentKnown = "";
		String currentObtained = "";
		String currentWatched = "";
		
		this.longestKnown = 0;
		this.longestObtained = 0;
		this.longestWatched = 0;
		
		for(int i = 0; i < miniDatabase.length; i++)
		{
			currentKnown += list[miniDatabase[i]].getTotal();
			currentObtained += list[miniDatabase[i]].getObtained();
			currentWatched += list[miniDatabase[i]].getWatched();
			
			if(i == 0)
			{
				longestKnown = currentKnown.length();
				longestObtained = currentObtained.length();
				longestWatched = currentWatched.length();
			}
			else if(i >= 1)
			{
				if(currentKnown.length() > longestKnown)
				{
					this.longestKnown = currentKnown.length();
				}
				if(currentObtained.length() > longestObtained)
				{
					this.longestObtained = currentObtained.length();
				}
				if(currentWatched.length() > longestWatched)
				{
					this.longestWatched = currentWatched.length();
				}
			}
			
			currentKnown = "";
			currentObtained = "";
			currentWatched = "";
		}//end i for
		
		for(int j = 0; j < miniDatabase.length; j++)
		{
			currentKnown += list[miniDatabase[j]].getTotal();
			currentObtained += list[miniDatabase[j]].getObtained();
			currentWatched += list[miniDatabase[j]].getWatched();
			
			if(currentKnown.length() < longestKnown)
			{
				for(int k = 0; k < (longestKnown - currentKnown.length()); k++)
				{
					criticalList += " ";
				}
			}
			
			criticalList += list[miniDatabase[j]].getTotal() + " : ";
			
			if(currentObtained.length() < longestObtained)
			{
				for(int k = 0; k < (longestObtained - currentObtained.length()); k++)
				{
					criticalList += " ";
				}
			}
			
			criticalList += list[miniDatabase[j]].getObtained() + " : ";
			
			if(currentWatched.length() < longestWatched)
			{
				for(int k = 0; k < (longestWatched - currentWatched.length()); k++)
				{
					criticalList += " ";
				}
			}
			
			criticalList += list[miniDatabase[j]].getWatched() + " : ";
			criticalList += list[miniDatabase[j]].getMediaType() + " : ";
			criticalList += list[miniDatabase[j]].getCompletionStatus() + " : ";
			criticalList += list[miniDatabase[j]].getUpToDateStatus() + " | ";
			criticalList += list[miniDatabase[j]].getName() + "\n";
						
			currentKnown = "";
			currentObtained = "";
			currentWatched = "";
			
		}//end j for
		return criticalList;
	}
	
	//method separation
	
	public String formatInputFile()
	{
		String formattedInputFile = "";
		
		for(int i = 0; i < list.length; i++)
		{
			formattedInputFile += list[i].getName() + "_" 
									 + list[i].getTotal() + " " 
									 + list[i].getObtained() + " "
									 + list[i].getWatched() + "\n";
		}
		
		return formattedInputFile;
	}
	
	//method separation
	
	public String formatDatabase()
	{
		String formattedDatabase = "";
		
		for(int i = 0; i < list.length; i++)
		{
			formattedDatabase += 
			  "            Name:  " + list[i].getName() + "\n"
			+ "        Episodes:  " + list[i].getTotal() + "\n"
			+ "Episodes on file:  " + list[i].getObtained() + "\n"
			+ "Episodes watched:  " + list[i].getWatched() + "\n"
			+ "\n\n";
		}
		
		return formattedDatabase;
	}
}