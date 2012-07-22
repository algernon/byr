# byr

byr is a simplistic URL shortener, to be deployed on [heroku][1], with
a handful of features only:

 * Supports custom short URLs
 * Shortens automatically if no custom URL is supplied
 * Mappings are stored compactly in MongoDB
 * Offers a minimalistic index on the root URI

## API

To shorten an URL, send a `POST` request to the root URI of byr, with
the `longurl` parameter set to the URL to shorten. If successful, it
will return the shortened URL, as a `text/plain` message. Otherwise an
error is shown.

To use a custom short URL, send a `POST` request to the desired
shortened URI, again, with the `longurl` parameter set to the URL to
shorten.

For compatibility with [hotot][2]'s expectations, another API is
provided: sending a `GET` request to `/+/` with the URL to shorten
appended will behave as if it was `POST`ed to the root with `longurl`
set.

## Configuration

The application will look for three environment variables:

 * `PORT`: If set, byr will listen on the specified port.
 * `MONGOLAB_URI`: If set, will connect to MongoDB using the URI
   specified. Otherwise to localhost.
 * `BYR_BASE`: The base URL for the application, will be prepended to
   the ids used for shortening.

## How it works

The application uses the *idspool* collection in the database to keep
track of the highest automatically shortened URI id. The
base62-encoded value is then used as the id for the shortened URI.

The id-url mappings are stored in the *urls* collection.

## License

Copyright © 2012 Gergely Nagy <algernon@madhouse-project.org>

Distributed under the GNU GPL v3+.

 [1]: http://www.heroku.com/
 [2]: http://www.hotot.org/
