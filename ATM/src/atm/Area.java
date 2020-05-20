package atm;

/**
     * @ClassName: Area
     * @Description: 打印屏幕
     * @author Huang_shengjun
     * @date 2020年5月20日 上午11:21:40
     *
 */
public class Area {
	
	// 系统启动时,默认ATM状态为空闲,等待用户执行打印处理
		// 此变量代表显示屏上显示的文字
		private String text = "";
		
		public String getText() {
			return this.text;
		}
		
		public void setText(String text) {
			this.text = text;
		}
		
		/**
		 * 获取显示屏的状态字符串. Json格式
		 */
		public String toString() {
			String output = "{";
			output += "\"text\":\"" + this.text + "\"";
			output += "}";
			return output;
		}
}
