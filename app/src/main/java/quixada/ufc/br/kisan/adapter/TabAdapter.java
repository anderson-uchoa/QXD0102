package quixada.ufc.br.kisan.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import quixada.ufc.br.kisan.fragments.ListaAnunciosFragment;
import quixada.ufc.br.kisan.fragments.WishListFragment;


public class TabAdapter extends FragmentPagerAdapter {

    private Context context;
    static final int QTD_TABS = 2;
    private static final String[] tabTitles= {"Anúncios","WishList"};

    public TabAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0){
            ListaAnunciosFragment anunciosFragments = new ListaAnunciosFragment();
        return  anunciosFragments;

        }else{
            WishListFragment wishListFragment = new WishListFragment();
            return  wishListFragment;
        }

    }

    @Override
    public int getCount() {
        return QTD_TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
