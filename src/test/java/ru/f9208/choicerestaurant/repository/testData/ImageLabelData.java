package ru.f9208.choicerestaurant.repository.testData;

import ru.f9208.choicerestaurant.model.entities.ImageLabel;
import ru.f9208.choicerestaurant.repository.TestMatcher;


public class ImageLabelData {
    public static final int START_IMAGE_SEQ = 1;

    public static final int BEAR_GRIZZLY_LABEL_ID = START_IMAGE_SEQ;
    public static final int MEAT_HOME_LABEL_ID = START_IMAGE_SEQ + 1;
    public static final int WHITE_BEAR = START_IMAGE_SEQ + 2;

    public final static TestMatcher<ImageLabel> MATCHER_IMAGE = TestMatcher.usingIgnoreFieldsComparator(ImageLabel.class);
    public static final ImageLabel bearGrizzlyLabel = new ImageLabel("bearGrizzly", null, "/resources/pic/cone.JPG");
    public static final ImageLabel meatHomeLabel = new ImageLabel("meatHome", null, "/resources/pic/blueberry_on_table.JPG");
    public static final ImageLabel whiteBear = new ImageLabel("whiteBear", null, "/resources/pic/grass.JPG");
    public static final ImageLabel justOpened = new ImageLabel("openSpace", null, "/resources/pic/openSpace.JPG");

    static {
        bearGrizzlyLabel.setId(BEAR_GRIZZLY_LABEL_ID);
        meatHomeLabel.setId(MEAT_HOME_LABEL_ID);
        whiteBear.setId(WHITE_BEAR);
    }
}
