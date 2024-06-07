package org.repo.Imagedownload;

import org.repo.Imagedownload.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImagedownloadApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ImagedownloadApplication.class, args);
	}

	@Autowired
	private ImageService imageService;

	@Override
	public void run(String... args) throws Exception {
		imageService.DownloadImage("https://images.pexels.com/photos/20873207/pexels-photo-20873207/free-photo-of-a-sign-saying-welcome-to-colorful-colorado-standing-in-a-desert.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1");
	}
}
