/**
 * A class to store goods information in search pages
 *
 * @author Chen Yuetao
 */

public class SearchedGood {
    private final String goodsId;
    private final String goodsName;
    private final String goodsIntro;
    private final int goodsCategoryId;
    private final String goodsCoverImg;
    private final int originPrice;
    private final int sellingPrice;
    private final int stockNum;
    private final String tag;

    private final int shopId;
    private final String shopName;

    /**
     * Constructor.
     * @param goodsId Good's id
     * @param goodsName Good's name
     * @param goodsIntro Good's introduction
     * @param goodsCategoryId Good's category id
     * @param goodsCoverImg Good's cover image
     * @param originPrice Good's origin price
     * @param sellingPrice Good's selling price
     * @param stockNum Good's stock nummber
     * @param tag Good's tag
     * @param shopId Shop's id
     * @param shopName Shop's name
     */
    public SearchedGood(String goodsId,
                        String goodsName,
                        String goodsIntro,
                        int goodsCategoryId,
                        String goodsCoverImg,
                        int originPrice,
                        int sellingPrice,
                        int stockNum,
                        String tag,
                        int shopId,
                        String shopName) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsIntro = goodsIntro;
        this.goodsCategoryId = goodsCategoryId;
        this.goodsCoverImg = goodsCoverImg;
        this.originPrice = originPrice;
        this.sellingPrice = sellingPrice;
        this.stockNum = stockNum;
        this.tag = tag;
        this.shopId = shopId;
        this.shopName = shopName;
    }

    /**
     * Generate the JSON string.
     * @return JSON string
     */
    public String generateJSON() {
        StringBuffer result = new StringBuffer("{");

        result.append("\"goods_id\":");
        result.append("\"").append(goodsId).append("\",");

        result.append("\"goods_name\":");
        result.append("\"").append(goodsName).append("\",");

        result.append("\"goods_intro\":");
        result.append("\"").append(goodsIntro).append("\",");

        result.append("\"goods_category_id\":");
        result.append(goodsCategoryId);
        result.append(",");

        result.append("\"goods_cover_img\":");
        result.append("\"").append(goodsCoverImg).append("\",");

        result.append("\"original_price\":");
        result.append(originPrice);
        result.append(",");

        result.append("\"selling_price\":");
        result.append(sellingPrice);
        result.append(",");

        result.append("\"stock_num\":");
        result.append(stockNum);
        result.append(",");

        result.append("\"tag\":");
        result.append("\"").append(tag).append("\",");

        result.append("\"shop_id\":");
        result.append(shopId);
        result.append(",");

        result.append("\"shop_name\":");
        result.append("\"").append(shopName).append("\"");

        return result.append("}").toString();
    }


//    /**
//     * Set the good's id.
//     * @param goodsId Good's id
//     */
//    public void setGoodsId(String goodsId) {
//        this.goodsId = goodsId;
//    }
//
//    /**
//     * Set the good's name.
//     * @param goodsName Good's name.
//     */
//    public void setGoodsName(String goodsName) {
//        this.goodsName = goodsName;
//    }
//
//    /**
//     * Set the good's introduction.
//     * @param goodsIntro Good's introduction
//     */
//    public void setGoodsIntro(String goodsIntro) {
//        this.goodsIntro = goodsIntro;
//    }
//
//    /**
//     * Set the good's category id.
//     * @param goodsCategoryId Good's category id
//     */
//    public void setGoodsCategoryId(int goodsCategoryId) {
//        this.goodsCategoryId = goodsCategoryId;
//    }
//
//    /**
//     * Set the good's cover image.
//     * @param goodsCoverImg Good's cover image
//     */
//    public void setGoodsCoverImg(String goodsCoverImg) {
//        this.goodsCoverImg = goodsCoverImg;
//    }
//
//    /**
//     * Set the original price.
//     * @param originPrice Good's origin price.
//     */
//    public void setOriginPrice(int originPrice) {
//        this.originPrice = originPrice;
//    }
//
//    /**
//     * Set the selling price.
//     * @param sellingPrice Good's selling price
//     */
//    public void setSellingPrice(int sellingPrice) {
//        this.sellingPrice = sellingPrice;
//    }
//
//    /**
//     * Set the good's stock number.
//     * @param stockNum Good's stock number
//     */
//    public void setStockNum(int stockNum) {
//        this.stockNum = stockNum;
//    }
//
//    /**
//     * Set the tag.
//     * @param tag Tag.
//     */
//    public void setTag(String tag) {
//        this.tag = tag;
//    }
//
//    /**
//     * Set the shop id.
//     * @param shopId Shop's id
//     */
//    public void setShopId(int shopId) {
//        this.shopId = shopId;
//    }
//
//    /**
//     * Set the shop name.
//     * @param shopName Shop's name
//     */
//    public void setShopName(String shopName) {
//        this.shopName = shopName;
//    }

}
