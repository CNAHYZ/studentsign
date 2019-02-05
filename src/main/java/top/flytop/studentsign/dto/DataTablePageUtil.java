package top.flytop.studentsign.dto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName DataTablePageUtil
 * @Description TODO
 * @Auther Wonder-yz
 * @Date 2019/2/2 11:29
 * @Version 1.0
 */
public class DataTablePageUtil<T> {
    /*------------------DT自动请求的参数(Sent parameters) begin--------------------*/

    // 绘制计数器。这个是用来确保Ajax从服务器返回的是对应的。
    // 要求在服务器接收到此参数后再返回
    private int draw; // 第几次请求
    // 第一条数据的起始位置，比如0代表第一条数据
    private int start = 0;// 起止位置
    // 告诉服务器每页显示的条数
    private int length = 10; // 数据长度
    // 全局的搜索条件，条件会应用到每一列（ searchable需要设置为 true ）
    private String search;
    // 如果为 true代表全局搜索的值是作为正则表达式处理，为 false则不是。
    private boolean is_search;
    // 告诉后台那些列是需要排序的。 i是一个数组索引，对应的是 columns配置的数组，从0开始
    private int[] order;
    // 告诉后台列排序的方式， desc 降序 asc升序
    private String order_dir;
    // columns 绑定的数据源，由 columns.dataOption 定义。
    private String columns_data;
    // columns 的名字，由 columns.nameOption 定义。
    private String columns_name;
    // 标记列是否能被搜索,为true代表可以，否则不可以，这个是由 columns.searchableOption 控制
    private String columns_searchable;
    // 标记列是否能排序,为 true代表可以，否则不可以，这个是由 columns.orderableOption 控制
    private boolean is_orderable;

    /*------------------服务器需要返回的数据(Returned data) begin--------------------*/
    //必要。上面提到了，Datatables发送的draw是多少那么服务器就返回多少。
    //这里注意，出于安全的考虑，强烈要求把这个转换为整形，这是为了防止跨站脚本（XSS）攻击。
    // private int draw;

    //必要。即没有过滤的记录数（数据库里总共记录数）
    private int recordsTotal;

    //必要。过滤后的记录数（如果有接收到前台的过滤条件，则返回的是过滤后的记录数）
    private int recordsFiltered;

    //必要。表中中需要显示的数据。这是一个对象数组，或纯数组，纯数组前台不需要用 columns绑定数据，
    // 会自动按照顺序去显示，而对象数组则需要使用 columns绑定数据才能正常显示。
    private List<T> data;

    //可选。你可以定义一个错误来描述服务器出了问题后的友好提示
    private String error;
    /*------------------服务器需要返回的数据(Returned data) end--------------------*/

    //当前页码
    private int page_num;

    //每页数据
    private int page_size;

    public DataTablePageUtil() {

    }

    public DataTablePageUtil(HttpServletRequest request) {
        //开始的数据行数
        String start = request.getParameter("start");
        // 每页的数据数
        String length = request.getParameter("length");
        // DT传递的draw:
        String draw = request.getParameter("draw");

        String search = request.getParameter("search[value]");
        /*
         *转成int型，避免XSS攻击
         */
        this.setStart(Integer.parseInt(start));
        this.setLength(Integer.parseInt(length));
        this.setDraw(Integer.parseInt(draw));
        this.setSearch(search);
        //计算页码
        this.page_num = (Integer.parseInt(start) / Integer.parseInt(length)) + 1;
        /*
         * page_size 要与页面显示数length相等，否则分页数据不准
         */
        this.page_size = getLength();
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public boolean isIs_search() {
        return is_search;
    }

    public void setIs_search(boolean is_search) {
        this.is_search = is_search;
    }

    public int[] getOrder() {
        return order;
    }

    public void setOrder(int[] order) {
        this.order = order;
    }

    public String getOrder_dir() {
        return order_dir;
    }

    public void setOrder_dir(String order_dir) {
        this.order_dir = order_dir;
    }

    public String getColumns_data() {
        return columns_data;
    }

    public void setColumns_data(String columns_data) {
        this.columns_data = columns_data;
    }

    public String getColumns_name() {
        return columns_name;
    }

    public void setColumns_name(String columns_name) {
        this.columns_name = columns_name;
    }

    public String getColumns_searchable() {
        return columns_searchable;
    }

    public void setColumns_searchable(String columns_searchable) {
        this.columns_searchable = columns_searchable;
    }

    public boolean isIs_orderable() {
        return is_orderable;
    }

    public void setIs_orderable(boolean is_orderable) {
        this.is_orderable = is_orderable;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getPage_num() {
        return page_num;
    }

    public void setPage_num(int page_num) {
        this.page_num = page_num;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }
}