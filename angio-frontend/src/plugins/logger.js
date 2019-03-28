import log from "loglevel";
import * as prefixer from "loglevel-plugin-prefix";

log.setLevel(process.env.NODE_ENV === "production" ? "error" : "debug", true);

prefixer.reg(log);
prefixer.apply(log, {
  template: "%t [%l] %n - ",
  levelFormatter(level) {
    return level.toUpperCase();
  },
  nameFormatter: function(name) {
    return name || "root";
  },
  timestampFormatter(date) {
    return date.toLocaleTimeString();
  }
});
