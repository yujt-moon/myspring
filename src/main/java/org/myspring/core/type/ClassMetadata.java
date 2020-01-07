package org.myspring.core.type;

/**
 * @author yujiangtao
 * @date 2020/1/6 18:48
 */
public interface ClassMetadata {

    /**
     * Return the name of the underlying class.
     * @return
     */
    String getClassName();

    /**
     * Return whether the underlying class represents an interface.
     * @return
     */
    boolean isInterface();

    /**
     * Return whether the underlying class is marked as abstract.
     * @return
     */
    boolean isAbstract();

    /**
     * Return whether the underlying class is marked as 'final'.
     * @return
     */
    boolean isFinal();

    boolean isConcrete();

    /**
     * Return whether the underlying class has a super class.
     * @return
     */
    boolean hasSuperClass();

    /**
     * Return the name of super class of the underlying class,
     * or {@code null} if there is no super class defined.
     * @return
     */
    String getSuperClassName();

    /**
     * Return the names of all interfaces that the underlying class
     * implements, or an empty array if there are none.
     * @return
     */
    String[] getInterfaceNames();
}
