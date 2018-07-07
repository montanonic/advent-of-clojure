(ns advent2015.day17
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.math.combinatorics :as comb]))

(def liters 150)

(def container-sizes
  (->> (slurp (io/resource "../src/advent2015/day17.txt"))
       str/split-lines
       (map #(Integer/parseInt %))))

(defn uniqueify [items]
  (map-indexed (fn [i x] [i x]) items))

(defn de-uniqueify [uniqueified-items]
  (map second uniqueified-items))

; Equal-sized containers are still individually unique containers, so when
; finding subsets we must treat them as non-equal (unique) objects.
(defn subsets [containers]
  (->> containers
       uniqueify
       comb/subsets
       (map de-uniqueify)))

(defn solve-1 []
  (->> container-sizes
       subsets
       (filter #(= liters (apply + %)))
       count))

(defn solve-2 []
  (->> container-sizes
       (sort >)
       subsets
       (filter #(= liters (apply + %)))
       (partition-by count)
       first
       count))
