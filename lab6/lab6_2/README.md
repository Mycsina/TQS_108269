a) Technical debt is the term that describes the additional work that has to be done in the future in order to overcome the shortcomings that the current solution has - as in it not being futureproof and ready to be built in as a solid base for the future.

![a](screenshot.png)

b) All problems found are low priority code smells, with 2 being an unused import and 6 being a test class being public.

c) Code coverage is at 43.1%, with 26 lines that are not covered. It also reports 32 conditions to cover, but 30 of them are respective to a lombok @Data annotation, so I'm not sure of how meaningful that is.