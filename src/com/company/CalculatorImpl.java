package com.company;


import java.util.ArrayList;


public class CalculatorImpl implements Calculator {
    public String evaluate(String s) {
        ArrayList<String> l = new ArrayList<String>();
        char[] ch = s.toCharArray();
        if (s.matches("^[0-9]+^[+]+^[\\-]+^[/]+^[*]+^[.]+^[(]+^[)]"))
            return null; //проверка на то что выражение не содержит лишних символов
        String buf = "";
        int fl = 1;       //флаг для проверки закрывающей скобки
        for (int i = 0; i < s.length(); i++) {
            if (ch[i] == '(') {         //Считается выржение в скобках
                fl = 0;
                i++;
                ArrayList<String> test = new ArrayList<String>();   //Записываем выражение в скобках в ArrayList
                while (ch[i] != ')') {      //Считываем пока не встретим закрывающую скобку
                    if (Character.isDigit(ch[i]) || ch[i] == '.') { //Считываем число полностью а не посимвольно
                        buf = buf + ch[i];
                    } else {
                        test.add(buf);
                        buf = "";
                        if (ch[i + 1] != ')') {
                            test.add(Character.toString(ch[i]));
                            fl = 1;
                        }
                    }
                    i++;
                }
                test.add(buf);
                buf = "";
                l.add(computation(test)); //вызываем функцию, которая высчитывает значение выражения
            }
            if (Character.isDigit(ch[i]) || ch[i] == '.') { //Записываем число полность если оно находится не в скобках
                while (Character.isDigit(ch[i]) || ch[i] == '.') {
                    buf = buf + ch[i];
                    if (i == s.length() - 1) break;//проверка на конец строки
                    i++;
                }
                l.add(buf);
                buf = "";
            }
            if (ch[i] == '+' || ch[i] == '-' || ch[i] == '*' || ch[i] == '/') { //Запись знаков для выражения и проверка на соответсвие правильности
            //    if (i + 1 < s.length() && i - 1 >= 0 && ch[i - 1] != '+' && ch[i - 1] != '-' && ch[i - 1] != '*' && ch[i - 1] != '/' && ch[i + 1] != '+' && ch[i + 1] != '-' && ch[i + 1] != '*' && ch[i + 1] != '/')
                if (i + 1 < s.length() && i - 1 >= 0 && Character.toString(ch[i - 1]).matches("[0-9]|[(]|[)]")&& Character.toString(ch[i + 1]).matches("[0-9]|[(]|[)]"))
                    l.add(Character.toString(ch[i]));
                else return null;
            }
        }
        if (fl == 0) return null; // если не найдена закрывающая скобка то возвращаем null
        return String.format("%.3f", Float.parseFloat(computation(l))); //Вычисление всего выражения с округление до 3 знаков после запятой
    }

    public String computation(ArrayList<String> l) {
        String buf = "";
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).equals("*")) { //если умножение
                buf = Float.toString(Float.parseFloat(l.get(i - 1)) * Float.parseFloat(l.get(i + 1)));//Подсчет значений слева и справа от знака
                l.remove(i + 1); //удаляются уже использованые цифры и знак
                l.remove(i);
                l.remove(i - 1);
                l.add(i - 1, buf);//Добавляется результат
                i--;
                continue;
            }

            if (l.get(i).equals("/")) {
                buf = Float.toString(Float.parseFloat(l.get(i - 1)) / Float.parseFloat(l.get(i + 1)));
                l.remove(i + 1);
                l.remove(i);
                l.remove(i - 1);
                l.add(i - 1, buf);
                i--;

            }
        }

        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).equals("+")) {

                buf = Float.toString(Float.parseFloat(l.get(i - 1)) + Float.parseFloat(l.get(i + 1)));

                l.remove(i + 1);
                l.remove(i);
                l.remove(i - 1);
                l.add(i - 1, buf);
                i--;
                continue;
            }
            if (l.get(i).equals("-")) {
                if (i - 1 >= 0)
                    buf = Float.toString(Float.parseFloat(l.get(i - 1)) - Float.parseFloat(l.get(i + 1)));
                l.remove(i + 1);
                l.remove(i);
                l.remove(i - 1);
                l.add(i - 1, buf);
                i--;
            }
        }
        return l.get(0);
    }
}