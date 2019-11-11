# lifegame

A [re-frame](https://github.com/day8/re-frame) application designed to gaze to be or not to be; aka [lifegame](https://en.wikipedia.org/wiki/Conway's_Game_of_Life).

## Development Mode

### Compile css:

Compile css file once.

```
lein garden once
```

Automatically recompile css file on change.

```
lein garden auto
```

### Run application:

```
lein clean
lein dev
```

shadow-cljs will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:8280](http://localhost:8280).

## License
Copyright © 2019 sandmark

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
