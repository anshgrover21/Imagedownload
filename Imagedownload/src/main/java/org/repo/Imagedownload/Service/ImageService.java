package org.repo.Imagedownload.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class ImageService {


    private final WebClient webClient;

    @Value("${image.size.threashold}")
    private long threshold;

    @Autowired
    public ImageService(WebClient.Builder webClient){
        this.webClient = webClient.build();
    }

    public void DownloadImage(String imageUrl) throws Exception {


        Long clientResponse = webClient.head().uri(imageUrl).exchangeToMono(response -> {
            if (response.statusCode().isError()) {
                return null;
            }
            HttpHeaders headers = response.headers().asHttpHeaders();
            long contentLength = headers.getContentLength();

            if (contentLength == -1) {
                // Return an error Mono if the content length cannot be determined
                return null;
            }
            return Mono.just(contentLength);

        }).block();

        if(clientResponse==null){
            throw new Exception("Error in URL");
        }



        if(clientResponse > threshold)
        {
            throw new Exception("size is larger");
        }

        Mono<byte[]> imageMono = webClient.get()
                .uri(imageUrl)
                .accept(MediaType.APPLICATION_OCTET_STREAM)
                .retrieve()
                .bodyToMono(byte[].class);

        byte[] imageBytes = imageMono.block();
        if (imageBytes == null) {
            throw new Exception("Failed to download image");
        }

        String fileName = "downloaded_image.jpg";
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            outputStream.write(imageBytes);
        }

    }






}
