class SessionTest extends GroovyTestCase {

    void testDefaultSession() {
        def session = new Session()
        session.with {
            assert title==null
            assert type == SessionType.conference
            assert duration == 60
            assert speakers.empty
        }
    }

    void testSessionWithAuthor() {
        def session = new Session(
                title: 'Integrating Groovy into your Java Applications',
                type: SessionType.conference,
                duration: 90,
                speakers: [
                        new User(firstName: 'Cédric', lastName: 'Champeau')
                ]
        )
        session.with {
            assert title=='Integrating Groovy into your Java Applications'
            assert type == SessionType.conference
            assert duration == 90
            assert speakers.size() == 1
            assert speakers.firstName == [ 'Cédric' ]
            assert speakers.lastName == [ 'Champeau' ]
        }
    }
}
