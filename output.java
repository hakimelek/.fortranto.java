public class PPLProject
{
	public static void main(String [] args)
	{
		int I= 0;
		int J= 0;
		int K= 0;
		int L= 0;
		int M= 0;
		int N= 0;
		System.out.println("ADDITION");
		for (I=1; I < 25; I++)
		{
			for (N=1; N < 4; N++)
			{
				J = (int) (Math.random()*9)+1;
				K = (int) (Math.random()*9)+1;
				System.out.println("        "+J+" "+'+'+" "+K+" "+'=');

			}

		}
		System.out.println("");
		System.out.println("SUBTRACTION");
		for (I=1; I < 25; I++)
		{
			for (N=1; N < 4; N++)
			{
				J = (int) (Math.random()*26)+5;
				K = (int) (Math.random()*8)+2;
				if(J>=K){
					System.out.println("        "+J+" "+'-'+" "+K+" "+'=');
				}
				else{
					System.out.println("        "+K+" "+'-'+" "+J+" "+'=');

				}

			}

		}
		System.out.println("");
		System.out.println("MULTIPLICATION");
		for (I=1; I < 25; I++)
		{
			for (N=1; N < 4; N++)
			{
				J = (int) (Math.random()*9)+1;
				K = (int) (Math.random()*9)+1;
				System.out.println("        "+J+" "+'*'+" "+K+" "+'=');

			}

		}
		System.out.println("");
		System.out.println("DIVISION");
		for (I=1; I < 25; I++)
		{
			for (N=1; N < 4; N++)
			{
				J = (int) (Math.random()*26)+5;
				K = (int) (Math.random()*8)+2;
				if(J>=K){
					M = J%K;
					if(M==0){
						System.out.println("        "+J+" "+'/'+" "+K+" "+'=');
					}
					else{
						J = J-M;
						System.out.println("        "+J+" "+'/'+" "+K+" "+'=');

					}
				}
				else{
					M = K%J;
					if(M==0){
						System.out.println("        "+K+" "+'/'+" "+J+" "+'=');
					}
					else{
						K = K-M;
						System.out.println("        "+K+" "+'/'+" "+J+" "+'=');

					}

				}

			}

		}

	}

}
