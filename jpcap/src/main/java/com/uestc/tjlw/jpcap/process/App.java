package com.uestc.tjlw.jpcap.process;

import cn.hutool.core.util.ArrayUtil;
import com.uestc.tjlw.jpcap.JpcapApplication;
import com.uestc.tjlw.jpcap.encode.EnCode;
import net.sourceforge.jpcap.capture.PacketCapture;
import net.sourceforge.jpcap.capture.PacketListener;
import net.sourceforge.jpcap.net.Packet;
import net.sourceforge.jpcap.net.TCPPacket;
import net.sourceforge.jpcap.net.UDPPacket;


public class App
{
	public static void run() {
			while (true){
				System.out.println("hello pcap");
				PacketCapture jp = new PacketCapture();
				try {
					PacketListener listener = (Packet packet)->{
						if(packet instanceof TCPPacket){
							TCPPacket tcp = (TCPPacket)packet;
							System.out.println("incommit packet is a Tcp packet!");
							byte[] data =tcp.getData();
							byte[] head = tcp.getHeader();
							try {
								String isoData = new String(data, "ISO-8859-1");
								System.out.printf("incommit packet is a tcp packet with data %s\n!", isoData);
								System.out.printf("数据头长度 %s\n!", tcp.getHeaderLength());
								EnCode.enCode(head);
							} catch(Exception e){
								e.printStackTrace();
							}

						} else if (packet instanceof UDPPacket){
							UDPPacket udp = (UDPPacket)packet;
							byte[] data =udp.getData();
							byte[] head = udp.getHeader();
							try {
								String isoData = new String(data, "ISO-8859-1");
								System.out.printf("incommit packet is a tcp packet with data %s\n!", isoData);
								EnCode.enCode(udp);
							} catch(Exception e){
								e.printStackTrace();
							}
						}
					};

					String avaidev = jp.findDevice();
					jp.open("s2-eth1", true);
					System.out.println("open device success!");

					jp.setFilter("", true);

					jp.addPacketListener(listener);

					jp.capture(-1);

					Thread.sleep(10000);
				} catch(Exception e){
					e.printStackTrace();
				}
			}
	}
}
