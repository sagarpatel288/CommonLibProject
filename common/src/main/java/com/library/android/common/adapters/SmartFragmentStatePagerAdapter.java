package com.library.android.common.adapters;

import android.util.SparseArray;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


/**
 * 11/10/2019
 * Extension of FragmentStatePagerAdapter which intelligently caches
 * all active fragments and manages the fragment lifecycle.
 * Usage involves extending from SmartFragmentStatePagerAdapter as you would any other PagerAdapter.
 * <p>
 * $Long Description$
 * </p>
 * $Tight coupling with literal$ {@link #}
 *
 * @author srdpatel
 * @see <a href="https://guides.codepath.com/android/viewpager-with-fragmentpageradapter">Codepath</a>
 * @see <a href="https://stackoverflow.com/questions/14035090/how-to-get-existing-fragments-when-using-fragmentpageradapter">How does retrival of a fragment work</a>
 * @since $1.0$
 */
public abstract class SmartFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    // Sparse array to keep track of registered fragments in memory
    private SparseArray<Fragment> registeredFragments = new SparseArray<>();
    // comment by srdpatel: 11/10/2019 If you use weakReference, you can save some memory
    // comment by srdpatel: 11/10/2019 https://stackoverflow.com/questions/14035090/how-to-get-existing-fragments-when-using-fragmentpageradapter
    private SparseArray<WeakReference<Fragment>> weakReferenceFragmentArray = new SparseArray<>();
    private ViewGroup mViewPagerInstance;

    SmartFragmentStatePagerAdapter(ViewGroup viewPagerInstance, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mViewPagerInstance = viewPagerInstance;
    }

    /**
     * 11/10/2019
     * Returns the fragment to display for that page
     * <p>
     * <p>
     * Do NOT try to save references to the Fragments in getItem(),
     * because getItem() is not always called. If the Fragment
     * was already created then it will be retrieved from the FragmentManger
     * and not here (i.e. getItem() won't be called again).
     * </p>
     * $Tight coupling with literal$ {@link #}
     *
     * @author srdpatel
     * @see <a href="https://stackoverflow.com/questions/14035090/how-to-get-existing-fragments-when-using-fragmentpageradapter">How does this method work</a>
     * @since $1.0$
     */
    @Override
    public Fragment getItem(int position) {
        return getRegisteredFragment(mViewPagerInstance, position);
    }

    /**
     * 11/10/2019
     * This should handle even the orientation changes
     * <p>
     * $Long Description$
     * </p>
     * $Tight coupling with literal$ {@link #}
     *
     * @author srdpatel
     * @see <a href="https://gist.github.com/nesquena/c715c9b22fb873b1d259">Dynamic viewPager Adapter</a>
     * @since $1.0$
     */
    public Fragment getRegisteredFragment(ViewGroup pagerInstance, int position) {
        // comment by srdpatel: 11/10/2019 WeakReference is experimental and if it works, we better use it to save memory
        Fragment existingInstance = null;
        if (weakReferenceFragmentArray.size() > position && weakReferenceFragmentArray.get(position) != null){
            existingInstance = weakReferenceFragmentArray.get(position).get();
        }
        if (existingInstance == null) {
            existingInstance = registeredFragments.get(position);
        }
        if (existingInstance != null) {
            return existingInstance;
        } else {
            return (Fragment) instantiateItem(pagerInstance, position);
        }
    }


    /**
     * 11/10/2019
     * Register the fragment when the item is instantiated
     * <p>
     * Here we can finally safely save a reference to the created
     * Fragment, no matter where it came from (either getItem() or
     * FragmentManger). Simply save the returned Fragment from
     * super.instantiateItem() into an appropriate reference depending
     * on the ViewPager position.
     * </p>
     * $Tight coupling with literal$ {@link #}
     *
     * @author srdpatel
     * @see <a href="https://stackoverflow.com/questions/14035090/how-to-get-existing-fragments-when-using-fragmentpageradapter">How does this method work</a>
     * @since $1.0$
     */
    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        // comment by srdpatel: 11/10/2019 If weakReferences work, we should better use it to save some memory
        weakReferenceFragmentArray.put(position, new WeakReference<>(fragment));
        registeredFragments.put(position, fragment);
        return fragment;
    }

    // Returns the fragment for the position (if instantiated)

    // Unregister when the item is inactive
    @Override
    public void destroyItem(@NotNull ViewGroup container, int position, @NotNull Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }
}