import java.util.Vector;

/* *********

Не вводить отрицательные числа
Только целые числа(однако результат будет представлен вещественным числом)
Обрабатываются только четыре операции * / - + и скобки

******* */

public class Calculator{

    public static void main (String args[]){
	if (args.length != 1)
	{
	    System.out.println("You have to input 1 argument");
	    return ;
	}
	Vector<String> tokens = new Vector<String>();
	//Разбиение входной строки на числа, знаки и скобки
	for (char ch:args[0].toCharArray())
	{
	    switch (ch)
	    {
		case ('+'):
		    tokens.add(String.valueOf(ch));
		    continue;
		case ('-'):
		    tokens.add(String.valueOf(ch));
		    continue;
		case ('*'):
		    tokens.add(String.valueOf(ch));
		    continue;
		case ('/'):
		    tokens.add(String.valueOf(ch));
		    continue;
		case ('('):
		    tokens.add(String.valueOf(ch));
		    continue;
		case (')'):
		    tokens.add(String.valueOf(ch));
		    continue;
	    }
	    if (ch <= '9' && ch >= '0')
	    {
		if (tokens.isEmpty())
		{
		    tokens.add(String.valueOf(ch));
		    continue;
		}
		if (isSign(tokens.lastElement()))
		{
		    tokens.add(String.valueOf(ch));
		}
		else
		{
		    tokens.set(tokens.size() - 1, tokens.lastElement() + ch);
		}
		continue;
	    }
	    System.out.println("Unknown symbol " + ch);
	    return ;
	}
	System.out.println();
	/*for (int i = 0; i < tokens.size(); i++)
	{
	    System.out.println(tokens.elementAt(i));
	}*/
	// Вектор с числами, знаками и скобками готов
	// Перевод выражения в обратную польскую запись
	Vector<String> outStr = new Vector<String>();
	Vector<String> stack = new Vector<String>();
	for (int i = 0; i < tokens.size(); i++)
	{
	    String tmp = tokens.elementAt(i);
	    if (!isSign(tmp))
	    {
		outStr.add(tmp);
	    }
	    else
	    {
		if (tmp.equals("("))
		{
		    stack.add(tmp);
		    continue;
		}
		if (tmp.equals("+") || tmp.equals("-"))
		{
		    boolean set = true;
		    while (!stack.isEmpty())
		    {
			if (stack.elementAt(stack.size() - 1).equals("("))
			{
			    stack.add(tmp);
			    set = false;
			    break;
			}
			outStr.add(stack.elementAt(stack.size() - 1));
			stack.remove(stack.size() - 1);
		    }
		    if (set)
		    {
			stack.add(tmp);
		    }
		}
		if (tmp.equals("*") || tmp.equals("/"))
		{
		    boolean set = true;
		    while (!stack.isEmpty())
		    {
			if (stack.elementAt(stack.size() - 1).equals("(")
			    || stack.elementAt(stack.size() - 1).equals("+")
			    || stack.elementAt(stack.size() - 1).equals("-"))
			{
			    stack.add(tmp);
			    set = false;
			    break;
			}
			outStr.add(stack.elementAt(stack.size() - 1));
			stack.remove(stack.size() - 1);
		    }
		    if (set)
		    {
			stack.add(tmp);
		    }
		}
		if (tmp.equals(")"))
		{
		    if (stack.isEmpty())
		    {
			System.out.println("Error with braces");
			return ;
		    }
		    while (!stack.elementAt(stack.size() - 1).equals("("))
		    {
			outStr.add(stack.elementAt(stack.size() - 1));
			stack.remove(stack.size() - 1);
			if (stack.isEmpty())
			{
			    System.out.println("Error with braces.");
			    return ;
			}
		    }
		    stack.remove(stack.size() - 1);
		}
	    }
	}
	while (!stack.isEmpty())
	{
	    if (stack.elementAt(stack.size() - 1).equals("("))
	    {
		System.out.println("Error with braces.");
		return;
	    }
	    outStr.add(stack.elementAt(stack.size() - 1));
	    stack.remove(stack.size() - 1);
	}
	System.out.println("RPN:");
	for (int i = 0; i < outStr.size(); i++)
	{
	    System.out.println(outStr.elementAt(i));
	}
	//В outStr хранится выражение в обратной польской записи
	//Вычисление выражения
	Vector<String> numStack = new Vector<String>();
	for (int i = 0; i < outStr.size(); i++)
	{
	    String tmp = outStr.elementAt(i);
	    if (!isSign(tmp))
	    {
		numStack.add(tmp);
		continue;
	    }
	    if (numStack.size() < 2)
	    {
		System.out.println("Error with operands.");
		return;
	    }
	    double x1, x2;
	    x1 = Double.parseDouble(numStack.elementAt(numStack.size() - 2));
	    x2 = Double.parseDouble(numStack.elementAt(numStack.size() - 1));
	    if (tmp.equals("/") && x2 == 0)
	    {
		System.out.println("You cant divide to 0");
		return;
	    }
	    x1 = count(x1, x2, tmp);
	    numStack.remove(numStack.size() - 1);
	    numStack.set(numStack.size() - 1, Double.toString(x1));
	}
	if (numStack.size() > 1)
	{
	    System.out.println("Something wrong with expression");
	    return;
	}
	System.out.println("Answer = " + numStack.elementAt(0));
    }

    static private boolean isSign(String arg)
    {
	if (arg.equals("+") || arg.equals("-") || arg.equals("*") || arg.equals("/")
	     || arg.equals("(") || arg.equals(")"))
	{
	    return true;
	}
	return false;
    }

    static private double count(double x, double y, String sign)
    {
	if (sign.equals("+"))
	{
	    return x + y;
	}
	if (sign.equals("-"))
	{
	    return x - y;
	}
	if (sign.equals("*"))
	{
	    return x * y;
	}
	if (sign.equals("/"))
	{
	    return x / y;
	}
	return 0.0;
    }

}