
public class Main
{
	private static boolean testMode = false;
	public static void main(String[] args)
	{
		if (testMode == false) {
			new Menu();
		}
		else
		{
			new Game(false, "00:00", false, true, false, false);
		}

	}
}