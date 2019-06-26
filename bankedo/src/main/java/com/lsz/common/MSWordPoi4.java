package com.lsz.common;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author L.Hao
 * @version [版本号, 2014-8-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.FieldsDocumentPart;
import org.apache.poi.hwpf.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

public class MSWordPoi4 {

//    /**
//     * @param args
//     */
//    public static void main(String[] args)
//    {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("${sub}", "我的接口");
//        map.put("${item2.school}", "湖南大学");
//        map.put("${item2.address}", "湖南");
////        map.put("${item1.name}", "王五1");
////        map.put("${item1.numberStudent}", "编号002");
////        map.put("${item1.sex}", "男2");
////        map.put("${item1.age}", "19");
//        String srcPath = "D:\\temp\\1.doc";
//        readwriteWord(srcPath, map);
//    }

    /**
     * 实现对word读取和修改操作
     *
     * @param filePath word模板路径和名称
     * @param map      待填充的数据，从数据库读取
     */
    public static ByteArrayOutputStream readwriteWord(String filePath, Map<String, String> map) {
        InputStream in = null;
        in = MSWordPoi4.class.getResourceAsStream(filePath);
        HWPFDocument hdt = null;
        try {
            hdt = new HWPFDocument(in);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Fields fields = hdt.getFields();
        Iterator<Field> iterator = fields.getFields(FieldsDocumentPart.MAIN)
                .iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getType());
        }

        //读取word文本内容
        Range range = hdt.getRange();


        TableIterator it = new TableIterator(range);
        // 迭代文档中的表格
        if (it.hasNext()) {
            Table tb = (Table) it.next();
            // 迭代行，默认从0开始
            Table tcDataTable = tb.insertTableBefore((short) 2, 2);//column and row列数和行数
            tcDataTable.getRow(0).getCell(0).getParagraph(0).getCharacterRun(0).insertBefore("插入i行j列的内容");

        }

        // 替换文本内容
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String s = entry.getValue();
            if (s == null) {
                s = "";
            }
            range.replaceText(entry.getKey(), s);
        }
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();

        try {
            hdt.write(ostream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ostream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ostream;
    }
}