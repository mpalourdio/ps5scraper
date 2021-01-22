/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.projects.ps5scraper.services;

import com.mpalourdio.projects.ps5scraper.configuration.MailSenderProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
@Slf4j
public class ScheduledScraper {

    private final RestTemplate restTemplate;
    private final JavaMailSender javaMailSender;
    private final MailSenderProperties mailSenderProperties;

    public ScheduledScraper(
            RestTemplateBuilder restTemplateBuilder,
            JavaMailSender javaMailSender,
            MailSenderProperties mailSenderProperties) {
        this.restTemplate = restTemplateBuilder.build();
        this.javaMailSender = javaMailSender;
        this.mailSenderProperties = mailSenderProperties;
    }

    @Scheduled(cron = "${scrapper.cron-expression}")
    public void scrapTheSurface() {
        Arrays.stream(Crawlables.values()).forEach(c -> {
            try {
                var requestBody = restTemplate.getForObject(c.getWebsite(), String.class);
                if (null != requestBody && !requestBody.toLowerCase().contains(c.getToCheck().toLowerCase())) {
                    sendMail(c.getName());
                    return;
                }
                logStillNothing(c);
            } catch (HttpClientErrorException.NotFound e) {
                if (c.getName().equals(Crawlables.AUCHAN.getName())) {
                    //The Auchan status page returns 404. Their devs are n00b, really....
                    logStillNothing(c);
                    return;
                }
                log.error("Unexpected 404 error for {}", c.getName());
            } catch (Exception e) {
                log.error("Error during scrap for " + c.getName(), e);
            }
        });
    }

    private void logStillNothing(Crawlables c) {
        log.info(c.getName() + " : Still nothing :'(");
    }

    private void sendMail(String name) {
        var msg = new SimpleMailMessage();
        msg.setTo(mailSenderProperties.getTo());
        msg.setFrom(mailSenderProperties.getFrom());
        msg.setSubject(name.toUpperCase() + " AVAILABLE?! GOGOGO!");
        msg.setText("Run baby run"); //needed to avoid shitty MIME configuration

        javaMailSender.send(msg);
    }
}
