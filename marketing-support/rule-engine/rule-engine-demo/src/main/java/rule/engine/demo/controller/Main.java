package rule.engine.demo.controller;

import groovy.lang.Binding;
import groovy.util.Eval;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {
        String script = "def age = context.age;\n" +
                "if (age == null) {\n" +
                "    println(\"age = null\");\n" +
                "}\n" +
                "\n" +
                "if (age > 10) {\n" +
                "    println(\"age 大于 10\")\n" +
                "    return true\n" +
                "} else {\n" +
                "    println(\"age 小于 10\")\n" +
                "    return false\n" +
                "}\n";

        Binding binding = new Binding(); // 创建一个空的binding对象
        HashMap<String, Object> map = new HashMap<>();
        map.put("age", 12);
        Eval.me("context", map, script); // 将脚本传递给eval函数进行执行
    }
}