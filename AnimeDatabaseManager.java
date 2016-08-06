

import java.util.Scanner;
import java.io.*;

public class AnimeDatabaseManager
{
	public static void main(String [] args)throws FileNotFoundException
	{
		Scanner kb = new Scanner(System.in);
		PrintStream failsafeFiller;
		
		AnimeDatabase manager = new AnimeDatabase();
		
		try
		{
			manager.fillAnimeList("anime list.txt");
		}//end try
		catch(java.io.FileNotFoundException e)
		{						
			failsafeFiller = new PrintStream(new File("anime list.txt"));
			
			failsafeFiller.print("No Name_0 0 0 N/A N/A_N/A_");
			failsafeFiller.println();
			failsafeFiller.close();
			
			PrintStream failsafeSortFiller = new PrintStream(new File("Optional Sorting.txt"));
			
			failsafeSortFiller.print(0);
			failsafeSortFiller.close();
			
			manager.fillAnimeList("anime list.txt");
		}//end catch
		
		manager.findLongestName();
		
		Scanner optionalSort = new Scanner(new File("Optional Sorting.txt"));
		
		int conditionalStartupSorting = optionalSort.nextInt();
		
		if(conditionalStartupSorting == 0 && manager.getListLength() > 1)
		{
			manager.sortAnime();
			
			PrintStream sortingDisabler = new PrintStream(new File("Optional Sorting.txt"));
			sortingDisabler.print(1);
			sortingDisabler.close();
		}
		
		int choice;
		
		System.out.println("Welcome to the database, what do you want to do?");
		System.out.println();
		
		do
		{
			choice = menu(kb);
			
			if(choice == 1)
			{
				System.out.println();
				System.out.println("Number of Anime series:  " + manager.getListLength());
				System.out.println();
			}
			else if(choice == 2)
			{
				addAnime(manager, kb);
			}
			else if(choice == 3 || choice == 4 || choice == 5 || choice == 6)
			{
				addEpisode(manager, kb, choice);
			}
			else if(choice == 7 || choice == 8 || choice == 9 || choice == 10)
			{
				changeEpisodeCount(manager, kb, choice);
			}
			else if(choice == 11)
			{
				changeType(manager, kb);
			}
			else if(choice == 12 || choice == 13)
			{
				changeState(manager, kb, choice);
			}
			else if(choice == 14 || choice == 15 || choice == 16 || choice == 17)
			{
				displayAnime(manager, kb, choice);
			}
			else if(choice == 18)
			{
				System.out.println();
				writeDatabase(manager);
				choice = 18;
			}
		}while(choice != 18);
		
		
	}//end main
	
	public static int menu(Scanner kb)
	{
		System.out.println(" 1)Check how many anime series are in the list.\n"
							  + " 2)Add another anime series.\n"
							  + " 3)Add a known episode to a series.\n"
							  + " 4)Add an obtained episode to a series.\n"
							  + " 5)Add a watched episode to a series.\n"
							  + " 6)+1 to options 3, 4, and 5.\n"
							  + " 7)+x to options 3, 4, and 5.\n"
							  + " 8)Change the number of known episodes in a series.\n"
							  + " 9)Change the number of obtained episodes in a series.\n"
							  + "10)Change the number of watched episodes in a series.\n"
							  + "11)Change the data type for a series.\n"
							  + "12)Change the completion status for a series.\n"
							  + "13)Change the up-to-date status for a series.\n"
							  + "14)Display an anime's information from the database.\n"
							  + "15)Display anime you have, but haven't watched.\n"
							  + "16)Display the entire database.\n"
							  + "17)Display Anime that are not yet complete.\n"
							  + "18)Save and quit.");
		
		System.out.println();
		System.out.print("Choice:  ");
		int selection = getValidInt(kb);
		
/*		while(selection < 1 || selection > 15)
		{
			System.out.println("Invalid input, choose again.");
			System.out.print("Choice:  ");
			
			selection = kb.nextInt();
		}*/
		
		return selection;
			
	}//end menu
	
	//method separation
	
	public static void addAnime(AnimeDatabase manager, Scanner kb)throws FileNotFoundException
	{
		manager.createNewDatabase();
		kb.nextLine();
		System.out.print("Enter a name for the anime:  ");
		
		String animeName = kb.nextLine();
		System.out.println();
		
		manager.addNewSeries(animeName);
		manager.sortAnime();
	}
	
	//method separation
	
	public static void addEpisode(AnimeDatabase manager, Scanner kb, int choice)
	{
		kb.nextLine();
		
		System.out.print("Anime name:  ");
		
		String name = kb.nextLine();

		int index = manager.findAnime(new Anime(name, 0, 0, 0, "File", "[ ]", "[ ]"));
		
		try
		{
			manager.getAnimeName(index);
			
			switch(choice)
			{
				case 3:
				{
					manager.addAnimeKnown(index);
					
					break;
				}
				case 4:
				{
					manager.addAnimeObtained(index);

					break;
				}
				case 5:
				{
					manager.addAnimeWatched(index);
					break;
				}
				case 6:
				{
					manager.addAnimeKnown(index);
					manager.addAnimeObtained(index);
					manager.addAnimeWatched(index);
					
					break;
				}
			}//end switch
		}//end try
		catch(java.lang.ArrayIndexOutOfBoundsException e)
		{
			System.out.println();
			System.out.println("That doesn't spell the beginning of anything currently existing in the database.");
		}

		System.out.println();
	}
	
	//method separation
		
	public static void changeEpisodeCount(AnimeDatabase manager, Scanner kb, int choice)
	{
		kb.nextLine();
		
		System.out.print("Anime name:  ");
		
		String name = kb.nextLine();
		
		int index = manager.findAnime(new Anime(name, 0, 0, 0, "File", "[ ]", "[ ]"));
		
		int episodes;
		
		try
		{
			manager.getAnimeName(index);
			
			switch(choice)
			{
				case 7:
				{
					System.out.print("Enter value for number of episodes to add:  ");
					episodes = getOtherValidInt(kb, choice);
					
					manager.changeAnimeKnown(index, manager.getAnimeKnown(index) + episodes);
					manager.changeAnimeObtained(index, manager.getAnimeObtained(index) + episodes);
					manager.changeAnimeWatched(index, manager.getAnimeWatched(index) + episodes);
					
					break;
				}//end case 7
				case 8:
				{
					System.out.print("Enter new value for episodes known:  ");
					episodes = getOtherValidInt(kb, choice);

					manager.changeAnimeKnown(index, episodes);
					
					break;
				}//end case 8
				case 9:
				{
					System.out.print("Enter new value for episodes obtained:  ");
					episodes = getOtherValidInt(kb, choice);

					manager.changeAnimeObtained(index, episodes);
					
					break;
				}//end case 9
				case 10:
				{
					System.out.print("Enter new value for episodes watched:  ");
					episodes = getOtherValidInt(kb, choice);

					manager.changeAnimeWatched(index, episodes);

				}//end case 10
			}//end switch
		}//end try
		catch(java.lang.ArrayIndexOutOfBoundsException e)
		{
			System.out.println();
			System.out.println("That doesn't spell the beginning of anything currently existing in the database.");
		}
		System.out.println();
	}
	
	//method separation
	
	public static void changeType(AnimeDatabase manager, Scanner kb)
	{
		kb.nextLine();
		System.out.print("Anime name:  ");
		
		String name = kb.nextLine();
		
		int index = manager.findAnime(new Anime(name, 0, 0, 0, "File", "[ ]", "[ ]"));
		
		try
		{
			manager.getAnimeName(index);
			
			System.out.println("Enter either \"File\" or \"Disc\" for the data type.");
			System.out.print("New type:  ");
			
			String type = kb.nextLine();
			
			while(type.compareTo("File") != 0 && type.compareTo("Disc") != 0)
			{
				System.out.println("Invalid entry, try again.");
				System.out.print("New type:  ");
				
				type = kb.nextLine();
			}
			
			manager.changeMediaFormat(index, type);
		}//end try
		catch(java.lang.ArrayIndexOutOfBoundsException e)
		{
			System.out.println();
			System.out.println("That doesn't spell the beginning of anything currently existing in the database.");
		}
		System.out.println();
	}
	
	//method separation
	
	public static void changeState(AnimeDatabase manager, Scanner kb, int choice)
	{
		kb.nextLine();
		
		System.out.print("Anime name:  ");
		
		String name = kb.nextLine();
		
		int index = manager.findAnime(new Anime(name, 0, 0, 0, "File", "[ ]", "[ ]"));
		
		try
		{
			manager.getAnimeName(index);
			
			switch(choice)
			{
				case 12:
				{
					manager.changeCompletionStat(index);
					
					break;
				}
				case 13:
				{
					manager.changeUpToDateStat(index);
				}
			}//end switch
		}//end try
		catch(java.lang.ArrayIndexOutOfBoundsException e)
		{
			System.out.println();
			System.out.println("That doesn't spell the beginning of anything currently existing in the database.");
		}
		System.out.println();
	}
	
	//method separation
	
	public static void displayAnime(AnimeDatabase manager, Scanner kb, int choice)
	{
		switch(choice)
		{
			case 14:
			{
				kb.nextLine();
				
				System.out.print("Anime name:  ");
				
				String name = kb.nextLine();
								
				int index = manager.findAnime(new Anime(name, 0, 0, 0, "File", "[ ]", "[ ]"));
				
				try
				{
					manager.getAnimeName(index);
					
					System.out.println("_______________________________________________________________________");
					System.out.println();
					System.out.println("Episodes : Episodes : Episodes : Data : Completion : Up To Date | Anime");
					System.out.println("Known    : Obtained : Watched  : Type : Status     : Status     | Name");
					System.out.println("_______________________________________________________________________");
					System.out.println();
					System.out.print(manager.toSingleString(index));
					System.out.println("_______________________________________________________________________");
					System.out.println();
				}
				catch(java.lang.ArrayIndexOutOfBoundsException e)
				{
					System.out.println();
					System.out.println("That doesn't spell the beginning of anything currently existing in the database.");
					System.out.println();
				}
				
				break;
			}//end case 14
			
			case 15:
			{
				System.out.println("_________________________________________________________________________");
				System.out.println();
				System.out.println("Episodes : Episodes : Episodes : Data : Completion : Up To Date | Anime");
				System.out.println("Known    : Obtained : Watched  : Type : Status     : Status     | Name");
				System.out.println("_________________________________________________________________________");
				System.out.println();
				
				int miniDatabaseSize = 0;
				int [] miniDatabase = new int[1];
				for(int i = 0; i < manager.getListLength(); i++)
				{
					if(manager.getAnimeObtained(i) != manager.getAnimeWatched(i))
					{
						miniDatabaseSize ++;
					}
					
					if(i == manager.getListLength() - 1)
					{
						miniDatabase = new int [miniDatabaseSize];
					}
				}//end primary for
				
				int miniDatabaseIndex = 0;
				for(int i = 0; i < manager.getListLength(); i++)
				{
					if(manager.getAnimeObtained(i) != manager.getAnimeWatched(i))
					{
						miniDatabase [miniDatabaseIndex] = i;
						miniDatabaseIndex ++;
					}
				}//end secondary for
				
				System.out.print(manager.toCriticalString(miniDatabase));
				System.out.println("_________________________________________________________________________");
				System.out.println();
				kb.nextLine();
				
				break;
			}//end case 15
			
			case 16:
			{
				System.out.println("_________________________________________________________________________");
				System.out.println();
				System.out.println("Episodes : Episodes : Episodes : Data : Completion : Up To Date | Anime");
				System.out.println("Known    : Obtained : Watched  : Type : Status     : Status     | Name");
				System.out.println("_________________________________________________________________________");
				System.out.println();
				System.out.print(manager.toString());
				System.out.println("_________________________________________________________________________");
				System.out.println();
				kb.nextLine();
				
				break;
			}//end case 16
			
			case 17:
			{
				System.out.println("_________________________________________________________________________");
				System.out.println();
				System.out.println("Episodes : Episodes : Episodes : Data : Completion : Up To Date | Anime");
				System.out.println("Known    : Obtained : Watched  : Type : Status     : Status     | Name");
				System.out.println("_________________________________________________________________________");
				System.out.println();
				
				int miniDatabaseSize = 0;
				int [] miniDatabase = new int[1];
				for(int i = 0; i < manager.getListLength(); i++)
				{
					if(manager.getCompletionString(i).compareTo("[ ]") == 0)
					{
						miniDatabaseSize ++;
					}
					
					if(i == manager.getListLength() - 1)
					{
						miniDatabase = new int [miniDatabaseSize];
					}
				}//end primary for
				
				
				int miniDatabaseIndex = 0;
				for(int i = 0; i < manager.getListLength(); i++)
				{
					if(manager.getCompletionString(i).compareTo("[ ]") == 0)
					{
						miniDatabase [miniDatabaseIndex] = i;
						miniDatabaseIndex ++;
					}
				}//end secondary for
				
				System.out.print(manager.toCriticalString(miniDatabase));
				System.out.println("_________________________________________________________________________");
				System.out.println();
				kb.nextLine();
			}
		}//end switch

	}
	
	//method separation
	
	public static void writeDatabase(AnimeDatabase manager)throws FileNotFoundException
	{
		PrintStream databaseEditor = new PrintStream(new File("anime list.txt"));
		PrintStream databaseWriter = new PrintStream(new File("Anime Database.txt"));
		
		for(int i = 0; i < manager.getListLength(); i++)
		{
			databaseEditor.print(manager.getAnimeName(i) + "_" + manager.getAnimeKnown(i)
							+ " " + manager.getAnimeObtained(i) + " " + manager.getAnimeWatched(i)
							+ " " + manager.getFileFormat(i) + " " + manager.getCompletionStat(i)
							+ "_" + manager.getUpToDateStat(i) + "_");
			databaseEditor.println();
			
			databaseWriter.print("            Name:  " + manager.getAnimeName(i));
			databaseWriter.println();
			databaseWriter.print("        Episodes:  " + manager.getAnimeKnown(i));
			databaseWriter.println();
			databaseWriter.print("Episodes on file:  " + manager.getAnimeObtained(i));
			databaseWriter.println();
			databaseWriter.print("Episodes watched:  " + manager.getAnimeWatched(i));
			databaseWriter.println();
			databaseWriter.print("File Format Type:  " + manager.getFileFormat(i));
			databaseWriter.println();
			databaseWriter.print("Completion State:  " + manager.getCompletionStat(i));
			databaseWriter.println();
			databaseWriter.print("Up To Date State:  " + manager.getUpToDateStat(i));
			databaseWriter.println();
			databaseWriter.println();
		}
		databaseEditor.close();
		
		databaseWriter.close();
		
	}

	public static int getValidInt(Scanner kb)
	{
		int guess = getInt(kb);
		while (guess < 1 || guess > 18)
		{
			System.out.println("Invalid input, choose again.");
			System.out.print("Choice:  ");
			guess = getInt(kb);
		}

		return guess;
	}
	
	//Checks to make sure the value entered was an int.
	public static int getInt(Scanner kb)
	{
		while(!kb.hasNextInt())
		{
			kb.next();
			System.out.println("That's not an int, try again.");
			System.out.print("Choice:  ");
		}
		return kb.nextInt();
	}
	
	public static int getOtherValidInt(Scanner kb, int choice)
	{
		int episodeCount = getOtherInt(kb, choice);
		while (episodeCount < 0)
		{
			System.out.println("Invalid value, try again.");
			switch(choice)
			{
				case 6:
				{
					System.out.print("Enter new value for episodes known:  ");
					
					break;
				}
				case 7:
				{
					System.out.print("Enter new value for episodes obtained:  ");
					
					break;
				}
				case 8:
				{
					System.out.print("Enter new value for episodes watched:  ");
				}
			}//end switch
			episodeCount = getOtherInt(kb, choice);
		}//end while
		
		return episodeCount;
	}
	
	public static int getOtherInt(Scanner kb, int choice)
	{
		while(!kb.hasNextInt())
		{
			kb.next();
			System.out.println("That's not an int, try again.");
			switch(choice)
			{
				case 6:
				{
					System.out.print("Enter new value for episodes known:  ");
					
					break;
				}
				case 7:
				{
					System.out.print("Enter new value for episodes obtained:  ");
					
					break;
				}
				case 8:
				{
					System.out.print("Enter new value for episodes watched:  ");
				}
			}//end switch
		}//end while
		return kb.nextInt();
	}
}//end class