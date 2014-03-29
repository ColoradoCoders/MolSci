package com.co2.molsci.util;

public class MSUtils 
{
	public static boolean arrayContains(Object[] arr, Object obj)
	{
		for (Object o : arr)
			if (o.equals(obj))
				return true;
		
		return false;
	}
}