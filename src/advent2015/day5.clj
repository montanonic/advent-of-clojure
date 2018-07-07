(ns advent2015.day5
  (:require [clojure.string :as str]))

(defn twice-non-overlapping [s]
  (re-find #"([a-z]{2}).*\1" s))

(defn repeat-letter-with-gap [s]
  (re-find #"([a-z])[a-z]\1" s))

(defn solution-2 [s]
  (and (twice-non-overlapping s)
       (repeat-letter-with-gap s)))

(->> (slurp "src/advent2015/day5.txt") str/split-lines (filter solution-2) count)

