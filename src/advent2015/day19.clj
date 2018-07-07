;(ns advent2015.day19
; (:require [clojure.java.io :as io]
;           [clojure.string :as str]))
;
;(def raw
;  (->> (slurp (io/resource "../src/advent2015/day19.txt"))
;       str/split-lines))
;
;(def parsed-raw
;  (reduce
;    (fn [[m data] s]
;      (if-let [[_ from to] (re-matches #"(\w+) => (\w+)" s)]
;        [(conj m [from to]) data]
;        (if-let [[_ new-data] (re-matches #"(\w+)" s)]
;          [m new-data]
;          [m data])))
;    [[] nil]
;    raw))
;
;(def replacements (first parsed-raw))
;(def data (second parsed-raw))
;
;(defn nths [coll & ns]
;  (for [n ns]
;    (nth coll n)))
;
;(for [[from to] replacements, i (range (count data))]
;  )
;
;
;(defn replace-nth [data pred replacement])
;
;
;;(defn solve-1 []
;;  (reduce
;;    (fn [molecules [from to]] (+ sum (count (re-seq (re-pattern k) data))))
;;    #{}
;;    replacements))
