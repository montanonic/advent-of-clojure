(ns advent2015.day10
  (:require [clojure.string :as str])
  (:use [advent-of-clojure.utils]))

(def input 1321131112)

(def n-iters 40)

(defn digits [n]
  (map (compr str read-string) (str n)))

(defn solve-1 [ns]
  (->> ns
       (partition-by identity)
       (mapcat (fn [x] [(count x) (first x)]))))

(defn solution-1 [n]
  (loop [ns (digits input), n n]
    (if (> n 0)
      (recur (solve-1 ns) (dec n))
      (count ns))))
