package com.dev.ops.task2.task2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class HelloController {

	@GetMapping("/hi")
	public String hello() {
		String hostname = null;
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			hostname = localHost.getHostName();
		} catch (UnknownHostException e) {
			System.err.println("Could not determine local host: " + e.getMessage());
		}

		return "Node: " + hostname;
	}
}
