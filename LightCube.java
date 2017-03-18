/* Berkay from Chaos Computer Club Mannheim
 * Project:Light Group - Subproject:Light Cubes
 */

package berkay.light.cube; 

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import bekay.light.common.Led;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper; 

public class LightCube {
	public static int LED_COUNT = 34;
	private static ObjectMapper mapper;
	private List<Led> leds = new ArrayList<Led>();
	private String host;
	private int port;
	private Socket outsocket;

	public LightCube(String ip, int port) throws UnknownHostException,   
			IOException {
		this.host = ip;
		this.port = port;
		for (int i = 0; i < LED_COUNT; i++) {
			leds.add(new Led());
		}
		outsocket = new Socket(this.host, this.port);
	}

	private void send() throws Exception {
		String json = mapper.writeValueAsString(leds);
		System.out.println(json);

		PrintStream jsonOut = new PrintStream(outsocket.getOutputStream());
		jsonOut.print(json);
		jsonOut.flush();
	}

	public static void main(String[] args) throws Exception { 
		mapper = new ObjectMapper();

		Scanner scanner = new Scanner(System.in);
		LightCube l1 = new LightCube("192.168.2.107", 80);   
		//LightCube l2 = new LightCube("151.217.211.15", 80);
		System.out.println("Verbindung erfolgreich");
		while (true) {
			try {
				if (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] parts = line.split(" ");
					if (parts.length < 4 || parts.length > 5) {
						throw new IllegalStateException(
								"requires 3 or 4 numbers");
					}
					int remote = Integer.parseInt(parts[0]);
					int r = Integer.parseInt(parts[1]);
					int g = Integer.parseInt(parts[2]);
					int b = Integer.parseInt(parts[3]);

					LightCube ll = null;
					if (remote == 0) {
					    ll = l1;
					}
					if (remote == 1) {
						//ll = l2;
					}
					int index = -1;
					if (parts.length == 5) {
						index = Integer.parseInt(parts[4]);
					}
					if (index == -1) {
						// set all
						for (Led leds : ll.leds) {
							leds.setColor(r, g, b);
						}
					} else {
						if (index < 0) {
							index = 0;
						}
						if (index > LED_COUNT - 1) {
							index = LED_COUNT - 1;
						}
						Led led = ll.leds.get(index);
						led.setColor(r, g, b);
					}
				}
				//l2.send();
				l1.send();
			} catch (Exception e) {
				e.printStackTrace();
			}

			Thread.sleep(500);
		}
	}

}
