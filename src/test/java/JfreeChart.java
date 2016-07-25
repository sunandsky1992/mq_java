import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by ss on 16-6-18.
 */
public class JfreeChart extends ApplicationFrame {
    static int store4[] = {  0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 , 216 , 224 , 232 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 , 240 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0};
    static int store3[] = {  0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 , 448 , 464 , 480 , 496 , 512 , 528 , 544 , 240 , 260 , 280 , 300 , 320 , 340 , 360 , 380 , 400 , 420 , 440 , 460 , 480 , 500 , 520 , 540 , 560 , 364 , 376 , 388 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 400 , 476 , 472 , 468 , 464 , 460 , 456 , 452 , 448 , 444 , 440 , 436 , 432 , 428 , 424 , 420 , 416 , 412 , 408 , 404 , 400 , 396 , 392 , 388 , 384 , 380 , 376 , 372 , 368 , 364 , 360 , 356 , 352 , 348 , 344 , 340 , 336 , 332 , 328 , 324 , 320 , 380 , 360 , 340 , 320 , 300 , 280 , 260 , 240 , 220 , 200 , 180 , 160 , 140 , 120 , 100 ,  80 ,  60 ,  40 ,  20 ,   0};
    static int store2[] = {  0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 , 348 , 360 , 372 , 384 , 396 , 408 , 420 , 432 , 444 , 456 , 468 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 468 , 456 , 444 , 432 , 420 , 408 , 396 , 384 , 372 , 360 , 348 , 336 , 324 , 312 , 300 , 288 , 276 , 264 , 252 , 240 , 308 , 296 , 284 , 272 , 260 , 248 , 236 , 224 , 212 , 200 , 188 , 176 , 164 , 152 , 140 , 128 , 116 , 104 ,  92 ,  80 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0};
    static int store1[] = {  0 ,  20 ,  40 ,  60 ,  80 , 100 , 120 , 140 , 160 , 180 , 200 , 220 , 240 , 260 , 280 , 300 , 320 , 340 , 360 , 380 , 400 , 420 , 440 , 460 , 480 , 500 , 520 , 540 , 560 , 232 , 240 , 248 , 256 , 264 , 272 , 280 , 288 , 296 , 304 , 312 , 320 , 340 , 360 , 380 , 400 , 420 , 440 , 460 , 480 , 500 , 520 , 540 , 560 , 132 , 136 , 140 , 144 , 148 , 152 , 156 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 480 , 472 , 464 , 456 , 448 , 440 , 432 , 424 , 416 , 408 , 400 , 392 , 384 , 376 , 368 , 360 , 352 , 344 , 336 , 328 , 320 , 396 , 392 , 388 , 384 , 380 , 376 , 372 , 368 , 364 , 360 , 356 , 352 , 348 , 344 , 340 , 336 , 332 , 328 , 324 , 320 , 384 , 368 , 352 , 336 , 320 , 304 , 288 , 272 , 256 , 240 , 224 , 208 , 192 , 176 , 160 , 144 , 128 , 112 ,  96 ,  80 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0 ,   0};


    public JfreeChart(String title) throws IOException {
        super(title);
        this.setContentPane(createPanel()); //构造函数中自动创建Java的panel面板
    }

    public static CategoryDataset createDataset() //创建柱状图数据集
    {
        DefaultCategoryDataset dataset=new DefaultCategoryDataset();
        dataset.setValue(10,"a","管理人员");
        dataset.setValue(20,"b","市场人员");
        dataset.setValue(40,"c","开发人员");
        dataset.setValue(15,"d","其他人员");
        return dataset;
    }

    public static JFreeChart createChart(CategoryDataset dataset) //用数据集创建一个图表
    {
        JFreeChart chart= ChartFactory.createBarChart("hi", "人员分布",
                "人员数量", dataset, PlotOrientation.VERTICAL, true, true, false); //创建一个JFreeChart
        chart.setTitle(new TextTitle("某公司组织结构图",new Font("宋体",Font.BOLD+Font.ITALIC,20)));//可以重新设置标题，替换“hi”标题
        CategoryPlot plot=(CategoryPlot)chart.getPlot();//获得图标中间部分，即plot
        CategoryAxis categoryAxis=plot.getDomainAxis();//获得横坐标
        categoryAxis.setLabelFont(new Font("微软雅黑", Font.BOLD,12));//设置横坐标字体
        return chart;
    }

    public static JPanel createPanel() throws IOException {
        //JFreeChart chart =createChart(createDataset());
        JFreeChart chart = createChartLine();
        ChartUtilities.saveChartAsPNG(new File("/home/ss/store4.png"),chart,400,300);

        return new ChartPanel(chart); //将chart对象放入Panel面板中去，ChartPanel类已继承Jpanel
    }

    public static JFreeChart createChartLine() {
        Font titleFont = new Font("宋体",Font.BOLD+Font.ITALIC,20);
        Font font = new Font("微软雅黑", Font.BOLD,12);
        Color bgColor = new Color(255,255,255);

        XYSeries dataSet = new XYSeries("Random Data");
        for (int i =0;i<=240;i++) {
            dataSet.add(i * 1.0,(store4[i]) * 1.0);
        }
        XYSeriesCollection data = new XYSeriesCollection(dataSet);


        JFreeChart chart = ChartFactory.createXYLineChart("", "", "", data,
                PlotOrientation.VERTICAL, // 绘制方向
                false, // 显示图例
                true, // 采用标准生成器
                false // 是否生成超链接
        );
        chart.getTitle().setFont(titleFont); // 设置标题字体
//        chart.getLegend().setItemFont(font);// 设置图例类别字体
        chart.setBackgroundPaint(bgColor);// 设置背景色
        //获取绘图区对象
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY); // 设置绘图区背景色
        plot.setRangeGridlinePaint(Color.WHITE); // 设置水平方向背景线颜色
        plot.setRangeGridlinesVisible(false);// 设置是否显示水平方向背景线,默认值为true
        plot.setDomainGridlinePaint(Color.WHITE); // 设置垂直方向背景线颜色
        plot.setDomainGridlinesVisible(false); // 设置是否显示垂直方向背景线,默认值为false


        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setLabelFont(font); // 设置横轴字体
        domainAxis.setTickLabelFont(font);// 设置坐标轴标尺值字体
        domainAxis.setLowerMargin(0.01);// 左边距 边框距离
        domainAxis.setUpperMargin(0.06);// 右边距 边框距离,防止最后边的一个数据靠近了坐标轴。
        domainAxis.setTickUnit(new NumberTickUnit(20));

        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setLabelFont(font);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());//Y轴显示整数
        rangeAxis.setAutoRangeMinimumSize(50);   //最小跨度
        rangeAxis.setUpperMargin(0.18);//上边距,防止最大的一个数据靠近了坐标轴。
        rangeAxis.setLowerBound(0);   //最小值显示0
        rangeAxis.setUpperBound(600);
        rangeAxis.setAutoRange(false);   //不自动分配Y轴数据
        rangeAxis.setTickMarkStroke(new BasicStroke(1.6f));     // 设置坐标标记大小
        rangeAxis.setTickMarkPaint(Color.BLACK);     // 设置坐标标记颜色



        // 获取折线对象
//        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
//        BasicStroke realLine = new BasicStroke(1.8f); // 设置实线
//        // 设置虚线
//        float dashes[] = { 5.0f };
//        BasicStroke brokenLine = new BasicStroke(2.2f, // 线条粗细
//                BasicStroke.CAP_ROUND, // 端点风格
//                BasicStroke.JOIN_ROUND, // 折点风格
//                8f, dashes, 0.6f);
//        for (int i = 0; i < dataSet.getRowCount(); i++) {
//            if (i % 2 == 0)
//                renderer.setSeriesStroke(i, realLine); // 利用实线绘制
//            else
//                renderer.setSeriesStroke(i, brokenLine); // 利用虚线绘制
//        }

        plot.setNoDataMessage("无对应的数据，请重新查询。");
        plot.setNoDataMessageFont(titleFont);//字体的大小
        plot.setNoDataMessagePaint(Color.RED);//字体颜色
        return chart;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        JfreeChart chart= new JfreeChart("");
        chart.pack();//以合适的大小显示
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}
