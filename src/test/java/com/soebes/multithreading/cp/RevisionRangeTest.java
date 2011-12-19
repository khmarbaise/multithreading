package com.soebes.multithreading.cp;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.testng.annotations.Test;

public class RevisionRangeTest {

    @Test
    public void revisionRangeBySize5() {
        RevisionRange rr = new RevisionRange(1, 20);
        
        List<RevisionRange> revisionRangeList = rr.getRevisionRangeBySize(5);
        assertThat(revisionRangeList).isNotNull().hasSize(4);

        //  1..5
        //  6..10
        // 11..15
        // 15..20
        assertThat(revisionRangeList.get(0).getFrom()).isEqualTo(1);
        assertThat(revisionRangeList.get(0).getTo()).isEqualTo(5);

        assertThat(revisionRangeList.get(1).getFrom()).isEqualTo(6);
        assertThat(revisionRangeList.get(1).getTo()).isEqualTo(10);

        assertThat(revisionRangeList.get(2).getFrom()).isEqualTo(11);
        assertThat(revisionRangeList.get(2).getTo()).isEqualTo(15);

        assertThat(revisionRangeList.get(3).getFrom()).isEqualTo(16);
        assertThat(revisionRangeList.get(3).getTo()).isEqualTo(20);
    }

    @Test
    public void revisionRangeBySize35() {
        RevisionRange rr = new RevisionRange(1, 100);
        
        List<RevisionRange> revisionRangeList = rr.getRevisionRangeBySize(35);
        assertThat(revisionRangeList).isNotNull().hasSize(3);

        //   1..35
        //  36..70
        //  71..105 => 71..100
        assertThat(revisionRangeList.get(0).getFrom()).isEqualTo(1);
        assertThat(revisionRangeList.get(0).getTo()).isEqualTo(35);

        assertThat(revisionRangeList.get(1).getFrom()).isEqualTo(36);
        assertThat(revisionRangeList.get(1).getTo()).isEqualTo(70);

        assertThat(revisionRangeList.get(2).getFrom()).isEqualTo(71);
        assertThat(revisionRangeList.get(2).getTo()).isEqualTo(100);

    }

}
