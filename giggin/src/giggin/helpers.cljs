(ns giggin.helpers)

(defn format-price[cents]
  (str " £" (/ cents 100)))