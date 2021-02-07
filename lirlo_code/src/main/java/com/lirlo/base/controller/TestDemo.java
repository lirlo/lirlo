package com.lirlo.base.controller;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellType;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.IOException;

public class TestDemo {

    public static void main(String[] args) throws DocumentException, IOException {
        //设置页面大小
        Rectangle rectPageSize = new Rectangle(PageSize.A3);// 定义A3页面大小
        rectPageSize = rectPageSize.rotate(); //横版
        Document document = new Document(rectPageSize, -80, -80, 50, 0); //边距
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream("C:\\Users\\lirlo\\Downloads\\PdfTable3.pdf"));

        //字体设置
        /*
         * 由于itext不支持中文，所以需要进行字体的设置，可以调用项目资源文件或者调用windows系统的中文字体，
         * 找到文件后，打开属性，将文件名及所在路径作为字体名即可。
         */
        //创建BaseFont对象，指明字体，编码方式,是否嵌入
        //创建Font对象，将基础字体对象，字体大小，字体风格
//        Font font = FontFactory.getFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED,10f, Font.NORMAL, BaseColor.BLACK);
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

        Font font = new Font(bfChinese, 10, Font.NORMAL);

        int rowNum = 0;
        int colNum = 0;
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(
                    new FileInputStream(new File("C:\\Users\\lirlo\\Downloads\\外来文办理简表.xlsx")));

            XSSFSheet sheet = workbook.getSheetAt(0);
            int column = sheet.getRow(0).getLastCellNum();
            int row = sheet.getPhysicalNumberOfRows();

            //下面是找出表格中的空行和空列
            List<Integer> nullCol = new ArrayList<>();
            List<Integer> nullRow = new ArrayList<>();
            for (int j = 0; j < column; j++) {
                int nullColNum = 0;
                for (int i = 0; i < row; i++) {
                    XSSFCell cell = sheet.getRow(i).getCell(j);
                    if (cell == null || (cell.getCellType() == CellType.STRING) && "".equals(cell.getStringCellValue())) {
                        nullColNum++;
                    }
                }
                if (nullColNum == row) {
                    nullCol.add(j);
                }
            }

            for (int i = 0; i < row; i++) {
                int nullRowNum = 0;
                for (int j = 0; j < column; j++) {
                    XSSFCell cell = sheet.getRow(i).getCell(j);
//					String str = cell.getStringCellValue();
                    if (cell == null || (cell.getCellType() == CellType.STRING) && "".equals(cell.getStringCellValue())) {
                        nullRowNum++;
                    }
                }
                if (nullRowNum == column) {
                    nullRow.add(i);
                }
            }
            PdfPTable table = new PdfPTable(column-sheet.getRow(0).getFirstCellNum());
            List<CellRangeAddress> ranges = sheet.getMergedRegions();

            PdfPCell cell1 = null;
            String str = null;
            for (int i = sheet.getFirstRowNum(); i < row; i++) {
                if (nullRow.contains(i)) { //如果这一行是空行，这跳过这一行
                    continue;
                }
                for (int j = sheet.getRow(0).getFirstCellNum(); j < column; j++) {
                    if (nullCol.contains(j)) { //如果这一列是空列，则跳过这一列
                        continue;
                    }
                    boolean flag = true;
                    XSSFCell cell = sheet.getRow(i).getCell(j);
                    if(cell.getCellType() == CellType.NUMERIC) {
                        str = cell.getNumericCellValue() + "";
                    }else{
                        str = cell.getStringCellValue();
                    }
                    for (CellRangeAddress range : ranges) { //合并的单元格判断和处理
                        if (j >= range.getFirstColumn() && j <= range.getLastColumn() && i >= range.getFirstRow()
                                && i <= range.getLastRow()) {
                            if (str == null || "".equals(str)) {
                                flag = false;
                                break;
                            }
                            rowNum = range.getLastRow() - range.getFirstRow() + 1;
                            colNum = range.getLastColumn() - range.getFirstColumn() + 1;
                            cell1 = mergeCell(str, font, rowNum, colNum);
                            table.addCell(cell1);
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        table.addCell(getPDFCell(str, font));
                    }
                }
            }

            workbook.close();
            document.open();
            document.add(table);
            document.close();
            writer.close();
            System.out.println("ok!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //合并
    public static PdfPCell mergeCell(String str, Font font, int i, int j) {

        PdfPCell cell = new PdfPCell(new Paragraph(str, font));
        cell.setMinimumHeight(25);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setRowspan(i);
        cell.setColspan(j);

        return cell;
    }
    //获取指定内容与字体的单元格
    public static PdfPCell getPDFCell(String string, Font font) {
        //创建单元格对象，将内容与字体放入段落中作为单元格内容
        PdfPCell cell = new PdfPCell(new Paragraph(string, font));

        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        //设置最小单元格高度
        cell.setMinimumHeight(25);
        return cell;
    }

}
