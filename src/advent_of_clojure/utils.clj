(ns advent-of-clojure.utils
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn compr [& fns]
  "Like comp, but composes in reverse order.
  (compr do-this then-that and-finally) === (comp and-finally then-that do-this)"
  (apply comp (reverse fns)))

(defmacro quick-map [& values]
  (into {} (for [value values] [(keyword value) value])))
