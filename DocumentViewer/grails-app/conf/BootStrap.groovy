import com.qualcomm.demo.Document
import grails.util.Environment
import org.joda.time.DateTime
import org.joda.time.DateTimeZone


class BootStrap {

    def init = { servletContext ->

        //creating a new method to supplement the ArrayList metaclass with a new random option, so we can create lots
        //of bootstrap data very easily (and randomly)
        ArrayList.metaClass.getRand = { ->
            return delegate[new Random().nextInt(delegate.size)]
        }

        def authors = ['Walt Whitman',
                       'Eduardo Gutierrez',
                       'Bryan Rubink',
                       'Leo Mejia',
                       'Andres Reyes',
                       'John Moore',
                       'Ron Skarupa',
                       'Ricardo Iriarte',
                       'Jeff Hunter',
                       'Dave Woodbury',
                       'Jay Katz',
                       'Courtney Canadeo',
                       'Barbara Casas',
                       'Martha Olvera',
                       'File Pomeranian',
                       'Buttercup Sloth',
                       'Velcro Sloth',
                       'Juan Padilla',
                       'Bharath Sithambaram',
                       'Anshu Singh',
                       'Janani Chandrababu',
                       'Dasari Arunkumar',
                       'Elavarasan Vinod'
        ]

        def pubDate = [generateUniversalDate(1855, 5, 16),
                       generateUniversalDate(1955, 3, 15),
                       generateUniversalDate(1982, 3, 4),
                       generateUniversalDate(1979, 4, 22),
                       generateUniversalDate(1955, 11, 30),
                       generateUniversalDate(1948, 8, 17),
                       generateUniversalDate(1810, 9, 16),
                       generateUniversalDate(1822, 10, 10),
                       generateUniversalDate(1910, 12, 1),
                       generateUniversalDate(1910, 11, 1),
                       generateUniversalDate(1776, 3, 1),
                       generateUniversalDate(1859, 8, 11),
                       generateUniversalDate(1824, 4, 15),
                       generateUniversalDate(1503, 6, 22),
                       generateUniversalDate(1966, 11, 30),
                       generateUniversalDate(1912, 8, 17),
                       generateUniversalDate(1834, 9, 16),
                       generateUniversalDate(1752, 2, 10),
                       generateUniversalDate(1910, 6, 8)
        ]


        def description = ['Leaves of Grass',
                           'Franklin Evans',
                           'Bryan Rubink',
                           'Drum-Taps',
                           'Memoranda During the War',
                           'Democratic Vistas',
                           'Le rive della Bormida nel 1794',
                           'Poor Folk',
                           'The Landlady',
                           'The Village of Stepanchikovo',
                           'Humiliated and Insulted',
                           'Brothers Karamazov',
                           'Rules for Revolutionaries',
                           'The Cat in the Hat',
                           'Davinci Code',
                           'Horton says Who?',
                           'Ghost Dad',
                           'Macbeth',
                           'Celebrated Living',
                           'American Way',
                           'You can have it all',
                           'The cat lady',
                           'The boy in the striped pajamas',
                           'How to abandon ship',
                           'Hard Girls',
                           'Cooking with Pooh',
                           'Family Violence'
        ]

        if(Environment.current == Environment.DEVELOPMENT) {
            for(i in 1..5000) {
                new Document(author: authors.rand, pubDate: pubDate.rand, description: description.rand).save(flush: true)
            }
        }

    }
    def destroy = {
    }

    //Generate a date in UTC based on the year, month, dayOfMonth combination
    private Date generateUniversalDate(int year, int month, int dayOfMonth) {
        Date date = new DateTime(year, month, dayOfMonth, 0, 0, DateTimeZone.UTC).toDate()
        return date
    }
}
