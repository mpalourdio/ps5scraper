/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mpalourdio.projects.ps5scraper.services;

import lombok.Getter;

/**
 * 'name' is just the name of the website you crawl
 * 'website' is the url you want to GET
 * 'toCheck' is just a string that needs to be be in the body of the HTTP response. If not present, the page has changed, and the PS5 might be there.
 *
 * Want to scrap another website ? And a value to this Enum and let it crawl...
 */
@Getter
public enum Crawlables {
    AMAZON("amazon", "https://www.amazon.fr/dp/B08H93ZRK9", "actuellement indisponible"),
    AUCHAN("auchan", "https://www.auchan.fr/sony-console-ps5-edition-standard/p-c1315865", "envolée");

    private final String name;
    private final String website;
    private final String toCheck;

    Crawlables(String name, String webSite, String toCheck) {
        this.name = name;
        this.website = webSite;
        this.toCheck = toCheck;
    }
}
