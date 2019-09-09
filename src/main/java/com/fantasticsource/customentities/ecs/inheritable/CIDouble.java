package com.fantasticsource.customentities.ecs.inheritable;


import com.fantasticsource.mctools.ecs.ECSEntity;
import com.fantasticsource.mctools.ecs.component.CDouble;
import com.fantasticsource.tools.Tools;

import java.util.ArrayList;
import java.util.Arrays;

public class CIDouble extends InheritableComponent<CDouble>
{
    public CIDouble(ECSEntity entity)
    {
        super(entity);
        calculatedComponentClass = CDouble.class;
    }

    @Override
    public void updateValidity()
    {
        boolean notEmpty = false;
        for (String token : Tools.fixedSplit(value, "[+\\-]"))
        {
            for (String token2 : Tools.fixedSplit(token.trim(), "[/*]"))
            {
                token2 = token2.trim();

                if (token2.equals("p")) notEmpty = true;
                else
                {
                    if (token2.equals(""))
                    {
                        valid = false;
                        return;
                    }

                    try
                    {
                        Double.parseDouble(token2);
                    }
                    catch (NumberFormatException e)
                    {
                        valid = false;
                        return;
                    }
                }
            }
        }

        valid = notEmpty;
    }

    @Override
    public CDouble getCalculatedComponent()
    {
        String[][] tokenArrays = Tools.preservedSplitSeparated(value, "[+\\-*/]");

        ArrayList<Double> values = new ArrayList<>();
        for (String s : tokenArrays[0])
        {
            s = s.trim();
            if (s.equals(""))
            {
                valid = false;
                return null;
            }
            else if (s.equals("p"))
            {
                values.add(getParentCalculatedComponent().value);
            }
            else
            {
                try
                {
                    double d = Double.parseDouble(s);
                    values.add(d);
                }
                catch (NumberFormatException e)
                {
                    valid = false;
                    return null;
                }
            }
        }


        ArrayList<String> operators = new ArrayList<>(Arrays.asList(tokenArrays[1]));

        int index1 = operators.indexOf("*"), index2 = operators.indexOf("/");
        int index = index1 == -1 ? index2 : index2 == -1 ? index1 : Tools.min(index1, index2);
        while (index >= 0)
        {
            if (operators.remove(index).equals("*"))
            {
                values.set(index, values.get(index) * values.remove(index + 1));
            }
            else
            {
                values.set(index, values.get(index) / values.remove(index + 1));
            }

            index1 = operators.indexOf("*");
            index2 = operators.indexOf("/");
            index = index1 == -1 ? index2 : index2 == -1 ? index1 : Tools.min(index1, index2);
        }

        index1 = operators.indexOf("+");
        index2 = operators.indexOf("-");
        index = index1 == -1 ? index2 : index2 == -1 ? index1 : Tools.min(index1, index2);
        while (index >= 0)
        {
            if (operators.remove(index).equals("+"))
            {
                values.set(index, values.get(index) + values.remove(index + 1));
            }
            else
            {
                values.set(index, values.get(index) - values.remove(index + 1));
            }

            index1 = operators.indexOf("+");
            index2 = operators.indexOf("-");
            index = index1 == -1 ? index2 : index2 == -1 ? index1 : Tools.min(index1, index2);
        }


        return new CDouble(entity).set(values.get(0));
    }

    @Override
    public CIDouble set(String value)
    {
        return (CIDouble) super.set(value);
    }

    @Override
    public CIDouble copy()
    {
        return new CIDouble(entity).set(value);
    }
}
