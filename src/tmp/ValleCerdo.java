package tmp;

import java.awt.Color;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Abraham Otero
 * @version 0.5
 */
public class ValleCerdo extends PicoCerdo  {


    public ValleCerdo() {
        //comment = "Write here your comment....";
        color = Color.BLUE;
        refreshBufferedImage();
    }

    @Override
    public String getName() {
        return "Valle";
    }

}
