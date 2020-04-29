/**
 * 
 */
package test;

import java.util.List;
import java.util.Scanner;

import com.bot.entity.Bot;

/**
 *@desc:һ�仰������
 *@author �˕D
 *@date:2020��2��17������3:01:33
 */
public class BotTest {
 
	/**
	 *@desc:һ�仰����
	 *@param args
	 *@return:void
	 * @throws Exception 
	 *@trhows
	 */
	public static void main(String[] args) throws Exception {
		Bot bot = Bot.getInstance();
		Scanner sc = new Scanner(System.in);
		String utterance = sc.next();
		while(!utterance.equals("�˳�")) {
			List<String> replys = bot.start(utterance);
			System.out.println("\n----------------------------------------------------------------");
			for(String reply: replys) {
				System.out.println(reply);
			}
			System.out.println("----------------------------------------------------------------\n");
			utterance = sc.next();
		}
		
	}

}
