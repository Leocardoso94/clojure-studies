(ns giggin.components.gigs
  (:require [giggin.state :as state]
            [giggin.helpers :refer [format-price]]))


(defn gig-item [{:keys [id img title price desc]}]
  [:div.gig {:key id}
   [:img.gig__artwork {:src img :alt title}]
   [:div.gig__body
    [:div.gig__title
     [:div.btn.btn--primary.float--right.tooltip {:data-tooltip "Add to cart"
                                                  :on-click #(swap! state/orders update id inc)}
      [:i.icon.icon--plus]] title]
    [:p.gig__price (format-price price)]
    [:p.gig__desc desc]]])

(defn gigs []
  [:main
   [:div.gigs (map gig-item (vals @state/gigs))]])




