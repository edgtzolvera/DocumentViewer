import com.qualcomm.demo.Document
import grails.util.Environment

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

        def pubDate = [new GregorianCalendar(1855, 5, 16).time,
                       new GregorianCalendar(1955, 3, 15).time,
                       new GregorianCalendar(1982, 3, 4).time,
                       new GregorianCalendar(1979, 4, 22).time,
                       new GregorianCalendar(1955, 11, 30).time,
                       new GregorianCalendar(1948, 8, 17).time,
                       new GregorianCalendar(1810, 9, 16).time,
                       new GregorianCalendar(1822, 10, 10).time,
                       new GregorianCalendar(1910, 12, 1).time,
                       new GregorianCalendar(1910, 11, 1).time,
                       new GregorianCalendar(1776, 3, 1).time,
                       new GregorianCalendar(1859, 8, 11).time,
                       new GregorianCalendar(1824, 4, 15).time,
                       new GregorianCalendar(1503, 6, 22).time,
                       new GregorianCalendar(1966, 11, 30).time,
                       new GregorianCalendar(1912, 8, 17).time,
                       new GregorianCalendar(1834, 9, 16).time,
                       new GregorianCalendar(1752, 2, 10).time,
                       new GregorianCalendar(1910, 6, 8).time
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
}
