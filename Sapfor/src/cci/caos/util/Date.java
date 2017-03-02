package cci.caos.util;

public final class Date {

	@SuppressWarnings("deprecation")
	public static java.sql.Date creerDate(int annee, int mois, int jour){
		int a = annee - 1900;
		int m = mois - 1;
		int j = jour;
		return new java.sql.Date(a, m, j);
	}
	
	
}
