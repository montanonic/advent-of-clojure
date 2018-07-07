(ns advent2015.day12
  (:require [cheshire.core :as cheshire]
            [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.walk :as walk]
            [com.rpl.specter :as S])
  (:use [advent-of-clojure.utils]))

(def data
  (->>
    (slurp (io/resource "../src/advent2015/day12.txt"))
    cheshire/decode))

(def solution-1
  (let [*sum (atom 0)]
    (walk/postwalk
      #(if (number? %) (swap! *sum + %))
      data)
    @*sum))

(def skip-reds
  (S/recursive-path
    [afn] p
    (S/cond-path
      (S/pred afn) S/STAY
      map? [(S/not-selected? S/MAP-VALS (S/pred= "red")) S/MAP-VALS p]
      coll? [S/ALL p])))

(def solution-2
  (transduce (S/traverse-all (skip-reds number?)) + data))
